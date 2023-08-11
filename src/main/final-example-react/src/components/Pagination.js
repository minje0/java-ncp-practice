import React from 'react';

const Pagination = () => {
  return (
    <div style="text-align: center;">
        <ul class="pagination" th:if="${boardList.totalElements > 0}"
            th:with="pageNumber = ${boardList.pageable.pageNumber},
                    pageSize = ${boardList.pageable.pageSize},
                    totalPages = ${boardList.totalPages},
                    startPage = ${T(java.lang.Math).floor(pageNumber / pageSize) * pageSize + 1},
                    tempEndPage = ${startPage + pageSize - 1},
                    endPage = ${tempEndPage > totalPages ? totalPages : tempEndPage}">
        <li class="pagination-btn" th:if="${pageNumber > 0}">
            <a th:href="@{/board/board-list(page=${pageNumber - 1})}">
            이전
            </a>
        </li>

        <li class="pagination-btn"
            th:each="page : ${#numbers.sequence(startPage, endPage)}">
            <a th:href="@{/board/board-list(page=${page - 1})}"
            th:text="${page}">

            </a>
        </li>

        <li class="pagination-btn" th:if="${pageNumber < totalPages - 1}">
            <a th:href="@{/board/board-list(page=${pageNumber + 1})}">
            다음
            </a>
        </li>
        </ul>
    </div>
  );
};

export default Pagination;