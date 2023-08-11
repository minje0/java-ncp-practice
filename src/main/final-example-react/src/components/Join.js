import React, {useCallback, useEffect, useState} from 'react';
import '../css/Join.css';
import {TextField} from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Join = () => {
    const navi = useNavigate();

    const [userName, setUserName] = useState('');
    const [userEmail, setUserEmail] = useState('');
    const [userPw, setUserPw] = useState('');
    const [userTel, setUserTel] = useState('');
    const [userBirth, setUserBirth] = useState('');
    const [userSchool, setUserSchool] = useState('');
    const [userAddress, setUserAddress] = useState('');

    const [checkEmail, setCheckEmail] = useState(false);
    const [pwValidation, setPwValidation] = useState(false);

    const changeUserName = useCallback((e) => {
        setUserName(() => e.target.value);
    }, []);

    const changeUserEmail = useCallback((e) => {
        setUserEmail(() => e.target.value);
    }, []);

    const changeUserPw = useCallback((e) => {
        setUserPw(() => e.target.value);
    }, []);

    //비밀번호 유효성 검사 메소드
    const validatePassword = (character) => {
        return /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{9,}$/.test(character);
    }

    //userPw state가 변경될 때마다 유효성 검사
    useEffect(() => {
        const pwValidationTag = document.getElementById('pwValidation');

        if(validatePassword(userPw)) {
            setPwValidation(() => true);
            pwValidationTag.style.display = 'none'; 
        } else {
            setPwValidation(() => false);
            pwValidationTag.style.display = 'block'; 
        }
    }, [userPw]);

    const onSubmit = useCallback((e) => {
        e.preventDefault();

        const join = async () => {
            const user = {
                name: userName,
                email: userEmail,
                password: userPw,
                tel: userTel,
                birth: userBirth,
                school: userSchool,
                address: userAddress
            };

            try {
                const response = await axios.post('http://localhost:8081/user/join', user);

                console.log(response);

                if(response.data && response.data.item.id) {
                    alert("회원가입이 완료되었습니다.");
                    navi('/login');
                }
            } catch(e) {
                console.log(e);
            }
        }

        join();
    }, [userName, userEmail, userPw, userTel, userBirth, userSchool, userAddress,checkEmail, pwValidation]);

  return (
    <div className="form-wrapper">
        <form id="modifyForm" onSubmit={onSubmit}>
            <h3>회원가입</h3>
            <div className="label-wrapper">
                <label htmlFor="userName">이름</label>
            </div>
            <div>
                <input type="text" id="userName" name="userName" required value={userName} onChange={changeUserName}></input>
            
            </div>

            <div className="label-wrapper">
                <label htmlFor="userEmail">이메일</label>
            </div>
            <div>
                <input type="text" id="userEmail" name="userEmail" required value={userEmail} onChange={changeUserEmail}></input>
            </div>

            <div className="label-wrapper">
                <label htmlFor="userPw">비밀번호</label>
            </div>
            <input type="password" id="userPw" name="userPw" required value={userPw} onChange={changeUserPw}></input>
            <p id="pwValidation" style={{color: 'red', fontSize: '0.8rem'}}>
            비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정해주세요.
            </p>
            <p id="pwCheckResult" style={{fontSize: '0.8rem'}}></p>

            <div className="label-wrapper">
                <label htmlFor="userTel">전화번호</label>
            </div>
            <input type="text" id="userTel" name="userTel" placeholder="숫자만 입력하세요." value={userTel} onChange={(e) => setUserTel(() => e.target.value)}></input>

            <div className="label-wrapper">
                <label htmlFor="userBirth">생년월일</label>
            </div>
            <input type="text" id="userBirth" name="userBirth" required value={userBirth}
            onChange={(e) => setUserBirth(() => e.target.value)}></input>
            
            <div className="label-wrapper">
                <label htmlFor="userSchool">학교</label>
            </div>
            <input type="text" id="userSchool" name="userSchool" required value={userSchool} onChange={(e) => setUserSchool(() => e.target.value)}></input>
            
            <div className="label-wrapper">
                <label htmlFor="userAddress">주소</label>
            </div>
            <input type="text" id="userAddress" name="userAddress" required value={userAddress} onChange={(e) => setUserAddress(() => e.target.value)}></input>

            <div style={{display: 'block', margin: '20px auto'}}>
                <button type="submit">회원가입</button>
            </div>
        </form>
    </div>
  );
};

export default Join;