import React, { useCallback, useState } from 'react';
import '../css/Login.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = ({setIsLogin}) => {
    const navi = useNavigate();

    const [userEmail, setuserEmail] = useState('');
    const [userPw, setUserPw] = useState('');

    const changeuserEmail = useCallback((e) => {
        setuserEmail(() => e.target.value);
    }, []);

    const changeUserPw = useCallback((e) => {
        setUserPw(() => e.target.value);
    }, []);

    const login = useCallback((e) => {
        e.preventDefault();

        const loginAxios = async () => {
            try{
                const response = await axios.post('http://localhost:8081/user/login', {email: userEmail, password: userPw});

                console.log(response);

                if(response.data && response.data.item.token) {
                    alert(`${response.data.item.name}님 환영합니다.`);
                    sessionStorage.setItem("ACCESS_TOKEN", response.data.item.token);
                    sessionStorage.setItem("userEmail", response.data.item.email);
                    sessionStorage.setItem("userName", response.data.item.name);
                    setIsLogin(true);
                    navi("/");
                }
            } catch(e) {
                console.log(e);
                if(e.response.data.errorMessage === 'id not exist') {
                    alert("이메일이 존재하지 않습니다.");
                    return;
                } else if(e.response.data.errorMessage === 'wrong pw') {
                    alert("비밀번호가 틀렸습니다.");
                    return;
                } else {
                    alert("알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.");
                    return;
                }
            }
        }

        loginAxios();
    }, [userEmail, userPw]);

  return (
    <div className="form-wrapper">
        <form id="loginForm" onSubmit={login}>
            <h3>로그인</h3>
            <div className="label-wrapper">
                <label htmlFor="userEmail">아이디</label>
            </div>
            <input type="text" id="userEmail" name="userEmail" required value={userEmail} onChange={changeuserEmail}></input>
            <div className="label-wrapper">
                <label htmlFor="userPw">비밀번호</label>
            </div>
            <input type="password" id="userPw" name="userPw" required value={userPw} onChange={changeUserPw}></input>
            <div style={{display: 'block', margin: '20px auto'}}>
                <button type="submit" id="btnLogin">로그인</button>
            </div>
        </form>
    </div>
  );
};

export default Login;