import React from 'react';
import {Link} from 'react-router-dom';

const BoardListItem = ({board}) => {
    const {id, title, content, writer, regdate, hits} = board;
  return (
    <tr>
        <td>{id}</td>
        <td>
            <Link to={`/board/${id}`}>
                {title}
            </Link>
        </td>
        <td>{writer}</td>
        <td>{regdate}</td>
        <td>{hits}</td>
    </tr>
  );
};

export default BoardListItem;