import Header from "../../components/Layout/Header.jsx";
import ContentContainer from "../../components/Layout/ContentContainer.jsx";
import RegisterForm from "../../components/Forms/AuthForm/RegisterForm.jsx";
import styles from "./AuthPage.module.css";
import {Link, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import Cookies from "js-cookie";

export default function RegisterPage() {
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
                        <span>Регистрация</span>
                    </div>
                    <RegisterForm/>
                    <div className={styles["form-footer-container"]}>
                        Уже есть аккаунт? <Link to="/login">Войти</Link>
                    </div>
                </div>
            </ContentContainer>
        </>
    );
}
