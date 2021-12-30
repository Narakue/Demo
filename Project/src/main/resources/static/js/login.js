function submit() {
    var profession = $("input:radio:checked").val();
    if (profession == "teacher") {
        // console.log("teacher");
        $.ajax({
            url: '/teacherLogin',
            type: 'post',
            data: {
                'id': $("#username").val(),
                "password": $("#password").val(),
                'remember': $("#remember").val(),
            },
            success: function (data) {
                if (data == "fail") {
                    alert("登陆失败");
                } else {
                    //登陆成功
                    window.location.href = "main_teacher.html";
                }
            }
        })
    } else {
        $.ajax({
            url: '/studentLogin',
            type: 'post',
            data: {
                'id': $("#username").val(),
                "password": $("#password").val(),
                'remember': $("#remember").val(),
            },
            success: function (data) {
                if (data == "fail") {
                    alert("登陆失败");
                } else {
                    //登陆成功
                    window.location.href = "main_student.html";
                }
            }
        })
    }
}

