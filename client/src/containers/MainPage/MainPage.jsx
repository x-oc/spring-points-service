import Graph from "../../components/Graph/Graph.jsx";
import HistoryTable from "../../components/HistoryTable/HistoryTable.jsx";
import LogOutButton from "../../components/Buttons/LogOutButton.jsx";
import PointForm from "../../components/Forms/PointForm/PointForm.jsx";
import axiosUtil from "../../utils/AxiosUtil.js";
import Header from "../../components/Layout/Header.jsx";
import ContentContainer from "../../components/Layout/ContentContainer.jsx";
import styles from "./MainPage.module.css";
import {useDispatch, useSelector} from "react-redux";
import {addToHistory} from "../../redux/HistorySlice.js";
import {useEffect} from "react";
import CleanTableButton from "../../components/Buttons/CleanTableButton.jsx";
import {loadPoints, loadUserData} from "../../utils/ServerDataLoadUtil.js";
import {useNavigate} from "react-router-dom";

export default function MainPage() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const username = useSelector(state => state.userReducer.username);

    function checkPoint(x, y, r) {
        axiosUtil
            .post("main/check-point", {x, y, r})
            .then((response) => {
                dispatch(addToHistory(response.data));
            });
    }

    useEffect(() => {
        loadUserData(dispatch, navigate);
        loadPoints(dispatch, navigate);
    }, []);

    return (
        <>
            <Header/>
            <ContentContainer>
                <div className={styles["menu-container"]}>
                    <div>
                        <div className={styles["menu-user-info-container"]}>
                            <p className={styles["username"]}>{username}</p>
                        </div>
                        <LogOutButton/>
                        <CleanTableButton/>
                    </div>
                    <div className={styles["graph-form-container"]}>
                        <PointForm pointChecker={checkPoint}/>
                        <Graph pointChecker={checkPoint}/>
                    </div>
                </div>

                <div className={styles["history-table-container"]}>
                    <HistoryTable/>
                </div>
            </ContentContainer>
        </>
    );
}
