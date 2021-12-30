var teacherId;

//内容配置
//查询所有老师
function getAllteacher() {
    var userdata = $("#allTeacher");
    $.ajax({
        url: '/student/queryAllTeacher',
        type: 'get',
        success: function (data) {
            userdata.empty();
            var allTeacher = JSON.parse(data);
            for (var i = 0; i < allTeacher.length; i++) {
                // <div className="active content" style="width:100px;height:20px">
                //     <button style="width:100px;height:20px">sbljc</button>
                // </div>
                var node = "<div className=\"active content\" style=\"width:100%;height:40px;margin-bottom: 5px;margin-top: 5px\"><button style=\"cursor:pointer;width:100%;height:100%; padding:1px;  margin=10px  ;background-color: #0e4c7d;  border-color: #294c7d;   color: #fff;  -moz-border-radius: 10px;    -webkit-border-radius: 10px;   border-radius: 10px; /* future proofing */    -khtml-border-radius: 10px; /* for old Konqueror browsers */    text-align: center;      vertical-align: middle;   border: 1px solid transparent;   font-weight: 900;    font-size:125%  ;border:solid 3px #6c9de1; \" onclick=\"getSubjectByTeacherId('" + allTeacher[i].id + "')\">" +
                    allTeacher[i].name + "</button></div>"
                userdata.append(node);
                console.log(node);
            }
        }
    })
}

//查询已申请的老师 待修改添加点击事件
function get_applicationTeacher() {
    var userdata = $("#applyTeacher");
    $.ajax({
        url: '/student/querySelectedTeacher',
        type: 'get',
        success: function (data) {
            userdata.empty();
            var applyTeacher = JSON.parse(data);
            // {"studentId":"123","name":"zyz","phone":0,"qq":0,"status":2,"subjectId":1,"subjectName":"题目1","teacherName":"0.0"}
            document.getElementById("teacher1").innerHTML = applyTeacher.teacherName;
            document.getElementById("subject1").innerHTML = applyTeacher.subjectName;
            // 1没选课，2选课中，3被选中，4被拒绝
            if (applyTeacher.status == 1) {
                document.getElementById("status1").innerHTML = "未选";
            } else if (applyTeacher.status == 2) {
                document.getElementById("status1").innerHTML = "申请中";
            } else if (applyTeacher.status == 3) {
                document.getElementById("status1").innerHTML = "已同意";
            } else if (applyTeacher.status == 4) {
                document.getElementById("status1").innerHTML = "已拒绝";
            }
            document.getElementById("phone1").innerHTML = applyTeacher.phone;
            document.getElementById("qq1").innerHTML = applyTeacher.qq;
        }
    })
}

// 论文管理展示
function get_subject_manager() {
    $.ajax({
        url: '/student/querySelectedTeacher',
        type: 'get',
        success: function (data) {
            // {"studentId":"123","name":"zyz","phone":0,"qq":0,"status":2,"subjectId":1,"subjectName":"题目1","teacherName":"0.0"}
            var subject_manager = JSON.parse(data);
            document.getElementById("teacher_name").innerHTML = subject_manager.teacherName;
            document.getElementById("subject_name").innerHTML = subject_manager.subjectName;
            document.getElementById("teacher_phone").innerHTML = subject_manager.phone;
            document.getElementById("teacher_qq").innerHTML = subject_manager.qq;
        }
    })
}

//查询每个老师的所有课题
function getSubjectByTeacherId(id) {
    teacherId = id;
    console.log(id);
    var subject = $("#subject");
    $.ajax({
        url: '/student/querySubjectByTeacherId?id=' + id,
        type: 'get',
        success: function (data) {
            subject.empty();
            var all_subject_by_teacher = JSON.parse(data);
            // [{"id":1,"name":"题目1","basicRequirement":"基本需求","researchObjective":"这是一个课题需求","reference":"参考文献","amount":1000,"selected":1,"passed":2,"teacherId":"t123","version":0},
            // {"id":3,"name":"name","basicRequirement":"basicRequirement","researchObjective":"researchObjective","reference":"reference","amount":10,"selected":0,"passed":1,"teacherId":"t123","url":"url","version":0},
            // {"id":5,"name":"课题zyz","basicRequirement":"需求","researchObjective":"目标","reference":"文献","amount":10,"selected":1,"passed":0,"teacherId":"t123","url":"D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211111\\0.020210918课堂复习404.docx","version":0}]
            var all_apply = 0;
            for (var i = 0; i < all_subject_by_teacher.length; i++) {
                var id = all_subject_by_teacher[i].id;
                var name = all_subject_by_teacher[i].name;
                var amount = all_subject_by_teacher[i].amount;
                var passed = all_subject_by_teacher[i].passed;
                var fileUrl = all_subject_by_teacher[i].url;
                var selected = all_subject_by_teacher[i].selected;
                var apply = amount - selected;//剩余人数
                all_apply += apply;
                var static = apply > 0 ? 1 : 0;

                var node = (i % 2 ? "<tr class=\"tr-temp-gray\" style=\"height: 60px;\">\n" : "<tr class=\"tr-temp\" style=\"height: 60px;\">\n") +
                    "       <td class=\"tr-index\">" + (i + 1) + "</td>\n" +
                    "       <td>" + name + "</td>\n" +
                    //待实现文件下载
                    "       <td onclick=\"download('" + fileUrl + "')\"> <a><img src='/img/biye.png'> </a> </td>" +
                    "       <td>" + amount + "</td>\n" +
                    "       <td>" + selected + "</td>\n" +
                    "       <td>" + passed + "</td>\n" +
                    "       <td>" + apply + "</td>\n";
                if (apply < 0) {
                    node += "   <td>不可选</td>\n" +
                        "       <td>已满</td>\n" +
                        "       </tr>\n";
                } else {
                    node += "<td class=\"status-full\" style='color: #0ea432'>可选</td>\n" +
                        "<td class=\"status-full\" onclick=\"selectSubject('" + id + "') \"style=\"cursor:pointer\">选择</td>";
                }
                subject.append(node);
            }
            if (all_apply > 0) {
                document.getElementById("apply_count").innerHTML = "当前老师名额状态"
                document.getElementById("refuse").innerHTML = "当前老师剩余总名额数:" + all_apply;
            } else {
                document.getElementById("apply_count").innerHTML = "当前老师名额状态"
                document.getElementById("refuse").innerHTML = "当前老师剩余总名额数:" + all_apply;
            }
        }
    })
}


//选课
function selectSubject(id) {
    $.ajax({
        url: '/student/selectSubject?id=' + id,
        type: 'get',
        success: function (data) {
            if (data == "选课成功") {
                alert("选课成功");
            } else if (data == "课程已满") {
                alert("课程已满");
            } else {
                alert("选课失败")
            }
            getSubjectByTeacherId(teacherId);
        },
        error: function (data) {
            alert("选课失败");
            getSubjectByTeacherId(teacherId);
        }
    })
}

// websocket连接的地址为：ws:// + websocket的URL
var websocket = new WebSocket("ws://localhost:8080/selectSubject");

//接收到消息的回调方法
websocket.onmessage = function (event) {
    getSubjectByTeacherId(teacherId);
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//退选
function cancelSubject() {
    $.ajax({
        url: '/student/cancelSubject',
        type: 'get',
        success: function (data) {
            if (data == "true") {
                alert("退选成功");
                get_applicationTeacher();
            } else {
                alert("退选失败");
                get_applicationTeacher();
            }
        },
    })
}

function modify(data) {
    document.getElementById("student_lujing").innerHTML = "当前位置: 河北师范大小毕业选题系统->:" + data;
}

function uploadFile() {
    $.ajax({
        url: '/student/uploadFile',
        type: 'get',
    })
}

function download(data) {
    window.location.href = "http://localhost:8080/download?path=" + data;
}