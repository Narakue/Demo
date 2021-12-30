var need = null;

// 根据教师id查询选择了该课程的学生
function select_student_by_teacher_id() {
    var userdata = $("#allTeacherThree");
    var userdata2 = $("#allTeacherThreeRight");
    $.ajax({
        url: '/teacher/selectStudentByTeacherId',
        type: 'get',
        success: function (data) {
            userdata.empty();
            userdata2.empty();
            var agreed_student = JSON.parse(data);
            for (var i = 0; i < agreed_student.length; i++) {
                // <div className="active content" id="allTeacherThree">
                //     <button style="width:100px;height:20px">sbljc</button>
                // </div>
                var node = '<div className=\"active content\" id=\"allTeacherThree\" onclick="get" style=\"width:100%;height:40px;margin-bottom: 5px;margin-top: 5px\">'
                    + '<button style=\"cursor:pointer;width:100%;height:100%; padding:1px;  margin=10px  ;background-color: #0e4c7d;  border-color: #294c7d;   color: #fff;  -moz-border-radius: 10px;    -webkit-border-radius: 10px;   border-radius: 10px; /* future proofing */    -khtml-border-radius: 10px; /* for old Konqueror browsers */    text-align: center;      vertical-align: middle;   border: 1px solid transparent;   font-weight: 900;    font-size:125%  ;border:solid 3px #6c9de1; \">' + agreed_student[i].name + '</button>'
                    + '</div>';
                userdata.append(node);
                console.log(node);
                // <tr className="tr-tempA">
                //     <td className="tr-index" style="width:8%">1</td>
                //     <td style="width:8%">sk</td>
                //     <td style="width:20%">如何教会zhb</td>
                //     <td style="width:8%">x</td>
                //     <td style="width:8%">4</td>
                //     <td style="width:8%">2</td>
                //     <td style="width:8%">0</td>
                //     <td style="width:8%">4</td>
                //     <td className="status-full" style="width:12%">同意</td>
                //     <td className="status-full" style="width:12%">拒绝</td>
                // </tr>
                // [{"studentId":"123","name":"zyz","phone":0,"qq":0,"status":2,"subjectId":1,"subjectName":"题目1"}]
                var node2 = (i % 2 ? "<tr className=\"tr-tempA\">" : "<tr className=\"tr-tempA\" style='background-color: #cecaca'>\n>")
                    + "<td className=\"tr-index\" style=\"width:8%;height:50px;border: #8a8a8a solid 1px\">" + agreed_student[i].studentId + "</td>"
                    + "<td style=\"width:8%;border: #8a8a8a solid 1px\">" + agreed_student[i].name + "</td>"
                    + "<td style=\"width:20%;border: #8a8a8a solid 1px\">" + agreed_student[i].subjectName + "</td>"
                    + " <td style=\"width:8%;border: #8a8a8a solid 1px\">" + "<img src='img/xiazai.png' onclick='download(" + agreed_student[i].url + ")'/>"+"</td>" // TODO:没找到数据
                    // + "<td style=\"width:8%\">4</td>" // TODO:没找到数据
                    // + "<td style=\"width:8%\">2</td>" // TODO:没找到数据
                    // + "<td style=\"width:8%\">0</td>" // TODO:没找到数据
                    // + "<td style=\"width:8%\">4</td>" // TODO:没找到数据
                    + "<td className=\"status-full\" style=\"width:12%;border: #8a8a8a solid 1px;color: #1f88be;cursor:pointer\" onclick=\"agree_student(" + agreed_student[i].studentId + "," + agreed_student[i].subjectId + ")\">同意</td>"
                    + "<td className=\"status-full\" style=\"width:12%;border: #8a8a8a solid 1px;color: red;cursor: pointer\" onclick='refuse_student(" + agreed_student[i].studentId + "," + agreed_student[i].subjectId + ")'>拒绝</td>";

                userdata2.append(node2);
                console.log(node2);
            }
        }
    })
}

// 同意已申请学生按钮
function agree_student(studentId, subjectId) {
    $.ajax({
        url: '/teacher/agreeStudent?studentId=' + studentId + '&subjectId=' + subjectId,
        type: 'get',
        success: function (data) {
            alert("同意成功");
            select_student_by_teacher_id();
        },
        error: function (data) {
            alert("操作失败");
            select_student_by_teacher_id();
        }
    })
}

// 拒绝已申请学生按钮
function refuse_student(studentId, subjectId) {
    $.ajax({
        url: '/teacher/refuseStudent?studentId=' + studentId + '&subjectId=' + subjectId,
        type: 'get',
        success: function (data) {
            alert("拒绝成功");
            select_student_by_teacher_id();
        },
        error: function (data) {
            alert("拒绝失败");
            select_student_by_teacher_id();
        }
    })
}

//查询老师已同意的学生
function get_agreed_student() {
    var userdata = $("#allTeacherTwo");
    var userdata2 = $("#all_agreed_student");
    $.ajax({
        url: '/teacher/queryStudent',
        type: 'get',
        success: function (data) {
            userdata.empty();
            var agreed_student = JSON.parse(data);
            for (var i = 0; i < agreed_student.length; i++) {
                // <div className="active content" id="allTeacherTwo">
                //     <button style="width:100px;height:20px">sbljc</button>
                // </div>

                // <tr className="tr-tempA">
                //     <td className="tr-index" style="width:8%;height:50px">1</td>
                //     <td style="width:8%">sk</td>
                //     <td style="width:20%">如何教会zhb</td>
                //     <td style="width:8%">x</td>
                //     <td style="width:8%">4</td>
                //     <td style="width:8%">2</td>
                //     <td style="width:8%">0</td>
                //     <td style="width:8%">4</td>
                // </tr>
                var name = agreed_student[i].name;
                var amount = agreed_student[i].amount;
                var passed = agreed_student[i].passed;
                var selected = agreed_student[i].selected;
                var apply = amount - selected;//剩余人数
                var node = '<div className=\"active content\" id=\"allTeacherTwo\" style=\"width:100%;height:40px;margin-bottom: 5px;margin-top: 5px\">'
                    + '<button style=\"cursor:pointer;width:100%;height:100%; padding:1px;  margin=10px  ;background-color: #0e4c7d;  border-color: #294c7d;   color: #fff;  -moz-border-radius: 10px;    -webkit-border-radius: 10px;   border-radius: 10px; /* future proofing */    -khtml-border-radius: 10px; /* for old Konqueror browsers */    text-align: center;      vertical-align: middle;   border: 1px solid transparent;   font-weight: 900;    font-size:125%  ;border:solid 3px #6c9de1; \">' + agreed_student[i].name + '</button>'
                    + '</div>';
                userdata.append(node);

                var node2 = (i % 2 ? "<tr className=\"tr-tempA\">\n" : "<tr className=\"tr-tempA\" style='background-color: #cecaca'>\n>\n") +
                    "                    <td className=\"tr-index\" style=\"width:8%;height:50px;border: #8a8a8a solid 1px\">" + (i + 1) + "</td>\n" +
                    "                    <td style=\"width:8%;border: #8a8a8a solid 1px\">" + name + "</td>\n" +
                    "                    <td style=\"width:20%;border: #8a8a8a solid 1px\">" + agreed_student[i].subjectName + "</td>\n" +
                    "                    <td style=\"width:8%;border: #8a8a8a solid 1px\">" + "<img src='img/xiazai.png' onclick='download(" + agreed_student[i].url + ")'/>"+"</td>\n" +
                    "                </tr>";

                userdata2.append(node2);
                console.log(node);
            }
        }
    })
}

// websocket连接的地址为：ws:// + websocket的URL
var websocket = new WebSocket("ws://localhost:8080/subject");

//接收到消息的回调方法
websocket.onmessage = function (event) {
    select_student_by_teacher_id();
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

// 根据老师id查询数据
function query_subject_by_teacher_id() {
    var userdata = $('#allTeacherOneRight');
    var userdata2 = $('#allTeacherOne');
    $.ajax({
        url: '/querySubjectByTeacherId',
        type: 'get',
        success: function (data) {
            // <tr className="tr-tempA">
            //     <td className="tr-index" style="width:8%">1</td>
            //     <td style="width:20%">如何教会ysy</td>
            //     <td style="width:8%">x</td>
            //     <td style="width:8%">4</td>
            //     <td style="width:8%">2</td>
            //     <td style="width:8%">0</td>
            //     <td style="width:8%">4</td>
            //     <td className="status-full" style="width:12%">同意</td>
            //     <td className="status-full" style="width:12%">拒绝</td>
            // </tr>
            userdata2.empty();
            userdata.empty();
            var applyTeacher = JSON.parse(data);
            for (var i = 0; i < applyTeacher.length; i++) {
                // [{"id":1,"name":"题目1","basicRequirement":"基本需求","researchObjective":"这是一个课题需求","reference":"参考文献","amount":100,"selected":1,"passed":3,"teacherId":"t123","version":101},
                // {"id":3,"name":"name","basicRequirement":"basicRequirement","researchObjective":"researchObjective","reference":"reference","amount":10,"selected":0,"passed":1,"teacherId":"t123","url":"url","version":0},
                // {"id":5,"name":"课题zyz","basicRequirement":"需求","researchObjective":"目标","reference":"文献","amount":10,"selected":0,"passed":0,"teacherId":"t123","url":"D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211111\\0.020210918课堂复习404.docx","version":0}]
                var id = applyTeacher[i].id;
                var name = applyTeacher[i].name;
                var amount = applyTeacher[i].amount;
                var passed = applyTeacher[i].passed;
                var selected = applyTeacher[i].selected;
                var apply = amount - selected;//剩余人数

                var node = (i % 2 ? "<tr className=\"tr-tempA\">\n" : "<tr className=\"tr-tempA\" style='background-color: #cecaca'>\n") +
                    "                <td className=\"tr-index\" style=\"width:8%;height:50px;border: #8a8a8a solid 1px\" >" + (i + 1) + "</td>\n" +
                    "                <td style=\"width:20%;border: #8a8a8a solid 1px\">" + name + "</td>\n" +
                    "                <td onclick='download(" + applyTeacher[i].url +")' style=\"width:8%;border: #8a8a8a solid 1px;\">" + "<img src='img/xiazai.png' />"+"</td>\n" +
                    "                <td style=\"width:8%;border: #8a8a8a solid 1px\">" + amount + "</td>\n" +
                    "                <td style=\"width:8%;border: #8a8a8a solid 1px\">" + (selected + passed) + "</td>\n" +
                    "                <td style=\"width:8%;border: #8a8a8a solid 1px\">" + passed + "</td>\n" +
                    "                <td style=\"width:8%;border: #8a8a8a solid 1px\">" + apply + "</td>\n" +
                    "                <td className=\"status-full\" style=\"width:12%;border: #8a8a8a solid 1px\"><a href = \"JavaScript:void(0)\" onclick = \"document.getElementById('light2').style.display='block';document.getElementById('fade2').style.display='block';onclickNew(" + id + ")\">修改</a></td>\n" +
                    "                <td className=\"status-full\" style=\"width:12%;border: #8a8a8a solid 1px;color: red;cursor:pointer\" onclick='delete_subject(" + id + ")'>删除</td>\n" +
                    "            </tr>"
                userdata.append(node);
                var node2 = "<div className=\"active content\" id=\"allTeacherOne\" style=\"width:100%;height:40px;margin-bottom: 5px;margin-top: 5px\">\n" +
                    "                    <button style=\"cursor:pointer;width:100%;height:100%; padding:1px;  margin=10px  ;background-color: #0e4c7d;  border-color: #294c7d;   color: #fff;  -moz-border-radius: 10px;    -webkit-border-radius: 10px;   border-radius: 10px; /* future proofing */    -khtml-border-radius: 10px; /* for old Konqueror browsers */    text-align: center;      vertical-align: middle;   border: 1px solid transparent;   font-weight: 900;    font-size:125%  ;border:solid 3px #6c9de1; \">" + name + "</button>\n" +
                    "                </div>"
                userdata2.append(node2);
                console.log(applyTeacher[i].url);
            }
        }
    })
}

// 这里是删除课题的函数
function delete_subject(id) {
    $.ajax({
        url: '/teacher/deleteSubject?id=' + id,
        type: 'get',
        success: function (data) {
            alert("删除成功");
            query_subject_by_teacher_id();
        },
        error: function (data) {
            alert("删除失败");
            query_subject_by_teacher_id();
        }
    })
}

// 插入用的函数
function onclickInsert() {
    insertSubject(document.getElementById('amount').value,
        document.getElementById('basic').value,
        document.getElementById('divName').value,
        document.getElementById('reference').value,
        document.getElementById('research').value,
        document.getElementById('url').value);
}

// 插入课题
function insertSubject(amount, basic, divName, reference, research, url){
    $.ajax({
        url: '/teacher/insertSubject?amount='+ amount +'&basicRequirement=' + basic +'&name=' + divName + '&reference=' + reference + '&researchObjective=' + research + '&url=' + url,
        type: 'get',
        success: function (data) {
            alert("插入成功");
            query_subject_by_teacher_id();
        },
        error: function (data) {
            alert("插入失败");
            query_subject_by_teacher_id();
        }
    })
}

// 修改用的函数
function onclickNewOne() {
    updateSubjectById(need,
        document.getElementById('amount2').value,
        document.getElementById('basic2').value,
        document.getElementById('divName2').value,
        document.getElementById('reference2').value,
        document.getElementById('research2').value,
        document.getElementById('url2').value);
}

// 修改课题
function updateSubjectById(id, amount, basic, divName, reference, research, url) {
    $.ajax({
        url: '/teacher/updateSubjectById?amount=' + amount + '&basicRequirement=' + basic + '&id=' + id + '&name=' + divName + '&reference=' + reference + '&researchObjective=' + research + '&url=' + url,
        type: 'get',
        success: function (data) {
            alert("修改成功");
            query_subject_by_teacher_id();
        },
        error: function (data) {
            alert("修改失败");
            query_subject_by_teacher_id();
        }
    })
}

function onclickNew(id) {
    need = id;
}

function modify(data) {
    document.getElementById("teacher_lujing").innerHTML = "当前位置: 河北师范大小毕业选题系统->:" + data;
}

function uploadFile() {
    $.ajax({
        url: '/teacher/uploadFile',
        type: 'get',
    })
}

function download(data) {
    $.ajax({
        url: data,
        type: 'get',
    })
}