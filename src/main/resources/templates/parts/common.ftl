<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Task Manager</title>

        <link th:href="@{/bootstrap/css/bootstrap.min.css}" rel="stylesheet" media="screen"/>
    </head>
    <body>
    <#nested>

    <script th:src="@{/jquery/jquery.min.js}"></script>
    <script th:src="@{/popper/popper.min.js}"></script>
    <script th:src="@{/bootstrap/js/bootstrap.min.js}"></script>
    </body>
    </html>
</#macro>