import React from 'react';

const FileList = ({boardFileList, addChangeFile, changeOriginFile}) => {
    const openFileInput = (vodFileNo) => {
        const fileInput = document.getElementById(`changeFile${vodFileNo}`);
        fileInput.click();
    }

    const changeBoardFile = (e, vodFileNo) => {
        console.log(e.target)
        const fileList = Array.prototype.slice.call(e.target.files);

        const changeFile = fileList[0];

        addChangeFile(changeFile);

        changeOriginFile(vodFileNo, "U", changeFile.name);

        const reader = new FileReader();

        reader.onload = function(ee) {
            const img = document.getElementById(`img${vodFileNo}`);

            const p = document.getElementById(`fileName${vodFileNo}`);

            img.src = ee.target.result;

            p.textContent = changeFile.name;
        }

        reader.readAsDataURL(changeFile);
    }

    const deleteImg = (e, boardFileNo) => {
        changeOriginFile(boardFileNo, "D", "");

        const ele = e.target;
        
        const div = ele.parentElement;

        div.remove();
    }

  return (
    <>
        {boardFileList && boardFileList.map(boardFile => (
            <div style={{display: 'inline-block',
                position: 'relative', width: '150px', height: '120px',
                margin: '5px', border: '1px solid #00f', zIndex: 1}}>
                <input type="file"
                        style={{display: 'none'}} id={`changeFile${boardFile.boardFileNo}`} onChange={(e) => changeBoardFile(e, boardFile.boardFileNo)}></input>
                <img style={{width: '100%', height: '100%', zIndex: 'none',
                    cursor: 'pointer'}} className="fileImg" id={`img${boardFile.vodFileNo}`} src={`http://localhost:8081/storage/download/${boardFile.vodSaveName}`} onClick={() => openFileInput(boardFile.vodFileNo)}></img>
                <input type="button" className="btnDel" value="x"
                        style={{width: '30px', height: '30px', position: 'absolute',
                    right: '0px', bottom: '0px', zIndex: 999,
                    backgroundColor: 'rgba(255, 255, 255, 0.1)',
                    color: '#f00' }} onClick={(e) => deleteImg(e, boardFile.vodFileNo)}></input>
                <p style={{display: 'inline-block',
                    fontSize: '8px', cursor: 'pointer'}}
                    id={`fileName${boardFile.vodFileNo}`}>
                        {boardFile.vodOriginName}
                </p>
            </div>
        ))}
    </>
  );
};

export default FileList;