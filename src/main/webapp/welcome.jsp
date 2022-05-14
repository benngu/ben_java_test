<%@ include file="/head.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

    <script type="text/javascript">
        const CONSTANTS = (() => {
            return {
                studentData: JSON.parse(JSON.parse(JSON.stringify('${studentDataMap}'))),
                departmentPassPercentageMap: convertListToMap('${deptPassPercentMap}')
            }
        })()

        $(function(){
            generateStudentTable();
            formatStudentTable($("#studentTable"));
        });

        function generateStudentTable() {
            let childrenHtml = "";
            let studentList = '${studentList}'.replace("[","").replace("]","").split(", ");

            for (let i=0; i<studentList.length; i++) {
                let student = CONSTANTS.studentData[studentList[i]];
                let departPassPercentage = CONSTANTS.departmentPassPercentageMap.get(student['department']);

                childrenHtml += '<tr><td>' + student['department'] + '</td>' +
                                '<td><a href="javaScript:void(0);" onclick="displayStudentName(this);">' + student['studentId'] + '</a></td>' +
                                '<td>' + student['marks'] + '</td>' +
                                '<td>' + departPassPercentage + '</td></tr>';
            }
            $("#studentTable").append(childrenHtml);
        }

        function logoutAccount() {
            $.ajax({
                type: 'POST',
                data: {'action': 'logout'},
                url: 'Welcome',
                success: function(data) {
                    window.location.replace('${pageContext.request.contextPath}'+'/login');
                }
            })
        }

        function displayStudentName(obj) {
            let params = {'action': 'getStudentName', 'studentId': obj.text};
            $.ajax({
                type: 'POST',
                data: params,
                url: 'Welcome',
                success: function(data) {
                    $(".alert-success").text("Student Name: " + data['studentName']).fadeIn('slow').delay('5000').fadeOut('slow');
                }
            })
        }
    </script>
</head>
<body>

    <div class="d-flex justify-content-between p-3">
        <h2>Welcome, ${userId}!</h2>
        <button class="rounded btn btn-primary" type="submit" onclick="logoutAccount();">Logout</button>
    </div>
    <div class="alert alert-success container text-center w-50" style="display:none;"></div>
    <div class="table-responsive p-5">
        <table id="studentTable" class="table table-bordered w-50 mx-auto text-center">
            <tr>
                <th>Department</th>
                <th>Student ID</th>
                <th>Marks</th>
                <th>Pass %</th>
            </tr>
        </table>
    </div>
</body>
</html>
