import React, { useState, useCallback } from "react";
import styled from "../components/Join/JoinStyled.module.css";
import axios from "axios";
import DaumAddress from "../components/Join/daumAddress";

const Join = () => {
    const [studentName, setStudentName] = useState('');
    const [grade, setGrade] = useState('');
    const [studentTel, setStudentTel] = useState('');
    const [studentEamil, setStudentEmail] = useState('');
    const [address, setAddress] = useState('');
    const [detailAddess, setDetailAddress] = useState('');

    const [parentName, setParentName] = useState('');
    const [parentTel, setParentTel] = useState('');
    const [parentEmail, setParentEmail] = useState('');

    const [birth, setBirth] = useState(new Date());

    const [school, setSchool] = useState('');
    const [schoolList, setSchoolList] = useState([]);


    const handleSchoolChange = (e) => {
        const value = e.target.value;
        setSchool(value);
        searchSchool(value);
      };
    
      const searchSchool = useCallback(async (searchTerm) => {
        if (!searchTerm) {
          setSchoolList([]); // 검색어가 없을 때 빈 리스트로 초기화
          return;
        }
    
        const neisUrl = 'https://open.neis.go.kr/hub/schoolInfo?KEY=';
        const KEY = '73cc2130c2024a7d8935ef4bb580679d';
        const neisData = '&Type=json&pIndex=1&pSize=5&SCHUL_NM=';
        const urlSum = `${neisUrl}${KEY}${neisData}${searchTerm}`;
    
        try {
          const response = await axios.get(urlSum, {
            headers: {
              'Content-Type': 'application/json; charset=UTF-8'
            }
          });
    
          if (
            response.data &&
            response.data.schoolInfo &&
            response.data.schoolInfo[1] &&
            response.data.schoolInfo[1].row
          ) {
            const schulNamesList = response.data.schoolInfo[1].row.map(
              (item) => item.SCHUL_NM
            );
            setSchoolList(schulNamesList);
          } else {
            setSchoolList([]);
          }
        } catch (e) {
          setSchoolList([]);
          console.log(e);
        }
      }, []);


    const onSubmit = useCallback((e) => {
        e.preventDefault();

        const join = async () => {
            const studentDTO = {
                name: studentName,
                birth: birth,
                school: school,
                grade: grade,
                tel: studentTel,
                email: studentEamil,
                address: address + ' ' + detailAddess
            };
            const parentDTO = {
                name: parentName,
                tel: parentTel,
                email: parentEmail,
                address: address + ' ' + detailAddess
            };
            const joinDTO = {
                studentDTO,
                parentDTO
            }
            try {
                console.log(joinDTO);
                const response = await axios.post('http://localhost:8081/user/join', joinDTO);
            } catch(e) {
                console.log(e);
            }
        }

        join();
    }, []);

    return (
        <>
        <div className={styled.joinDiv}>

            <div className={styled.joinTitle}>
                <label>GOGI 코딩학원</label>
                <h1 style={{marginTop: "0"}}>학생 등록</h1>
            </div>

        <form className={styled.joinForm} onSubmit={onSubmit}>
            <div className={styled.joinContent1}>
                <label>학생 성명</label>
                <input type="text" className="input" name="studentName" onChange={(e) => setStudentName(()=>e.target.value)}></input>

                <label>생년월일</label>
                <input type="text" name="birth" onChange={(e)=> setBirth(()=>e.target.value)}></input>

                <label>학교</label>
                
                
                    <input
                    type="text"
                    name="school"
                    value={school}
                    onChange={handleSchoolChange}
                    list="school-list"
                    autoComplete="false"
                    className={styled.searchSchoolInput}
                    ></input>
                    <datalist id="school-list">
                        {schoolList.map((schoolInfo, index) => (
                        <option key={index} value={schoolInfo} />
                        ))}
                    </datalist>

                
                <label>학년</label>
                <input type="text" name="grade" onChange={(e)=> setGrade(()=>e.target.value)}></input>

                <label>학생 연락처</label>
                <input type="text" name="studentTel" onChange={(e)=> setStudentTel(()=>e.target.value)}></input>

                <label>학생 Email</label>
                <input type="email" name="studentEamil" onChange={(e)=> setStudentEmail(()=>e.target.value)}></input>

                <label>학부모 성함</label>
                <input type="text" name="parentName" onChange={(e)=> setParentName(()=>e.target.value)}></input>
                
                <label>학부모 연락처</label>
                <input type="text" name="parentTel" onChange={(e)=> setParentTel(()=>e.target.value)}></input>

                <label>학부모 Email</label>
                <input type="email" name="parentEmail" onChange={(e)=> setParentEmail(()=>e.target.value)}></input>

                <label>차량</label>
                <input type="text"></input>
            </div>
        
            <div className={styled.joinContent2}>
                <label>상담 내용</label>
                <textarea className={styled.counsel}></textarea>

                <label>특이 사항</label>
                <textarea className={styled.significant}></textarea>

                <label>반 배정</label>
                <input type="text"></input>

                <label>수강 과목</label>
                <input type="text"></input>

                <label>주소</label>
                <DaumAddress setAddress={setAddress} />

                <label>상세 주소</label>
                <input type="text" name="detailAddress" placeholder="상세주소를 입력하세요." onChange={(e)=> setDetailAddress(()=>e.target.value)}>
                </input>
                

                <div className={styled.joinBtns}>
                    <button type="submit">보류하기</button>
                    <button type="submit">등록하기</button>
                </div>
            </div>

        </form>

        </div>
        </>
    );
};

export default Join;