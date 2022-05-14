<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">

        function convertListToMap(dataList) {
            const dataMap = new Map();
            dataList = dataList.replace("{","").replace("}","").split(", ");
            for (let i=0; i<dataList.length; i++) {
                let departmentName = dataList[i].split("=")[0];
                let percentage = dataList[i].split("=")[1];

                dataMap.set(departmentName, formatDoubleValue(percentage));
            }
            return dataMap;
        }

        function formatStudentTable(table) {
            table.each(function () {
                let columns = $('th', table.find('tbody')).length;
                for (let j=columns; j>=1; j--) {
                    let values = $(this).find("tr>td:nth-of-type("+j+")");
                    let run = 1;
                    for (let i=values.length-1;i>-1;i--){
                        if (values.eq(i).text()=== values.eq(i-1).text()){
                            values.eq(i).remove();
                            run++;
                        }else{
                            values.eq(i).attr("rowspan",run);
                            run = 1;
                        }
                    }
                }
            });
        }

        function formatDoubleValue(value) {
            if (value.split(".")[1] === '0') {
                return value.split(".")[0];
            }
            return value;
        }
    </script>
</head>
<body>

</body>
</html>
