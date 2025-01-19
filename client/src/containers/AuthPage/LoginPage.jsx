import Header from "../../components/Layout/Header.jsx";
import ContentContainer from "../../components/Layout/ContentContainer.jsx";
import LoginForm from "../../components/Forms/AuthForm/LoginForm.jsx";
import styles from "./AuthPage.module.css"
import {Link, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import Cookies from "js-cookie";

export default function LoginPage() {
    const navigate = useNavigate();

    useEffect(() => {
        if (Cookies.get("refreshToken")) {
            navigate("/main");
        }
    }, []);
    return (
        <>
            <Header/>
            <ContentContainer>
                <div className={styles["form-container"]}>
                    <div className={styles["form-header-container"]}>
                        <span>Вход</span>
                    </div>
                    <LoginForm/>
                    <div className={styles["form-footer-container"]}>
                        <Link to="/register">Зарегистрироваться</Link>
                    </div>
                </div>
            </ContentContainer>
        </>
    );
}
