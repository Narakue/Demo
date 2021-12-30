//header配置
//获取名字
function getName() {
    $.ajax({
        url: '/message',
        type: 'get',
        success: function (data) {
            var my_data = JSON.parse(data);
            // console.log(my_data.name);
            document.getElementById("name").innerHTML = my_data.name;
        }
    })
}

//退出
function exit() {
    $.ajax({
        url: '/signOut',
        type: 'get',
        success: function (data) {
            if (data == "fail") {
                alert("退出失败");
            } else {
                //登陆成功
                window.location.href = "login.html";
            }
        }
    })
}

//获取日期
function getDaysBetween(date1, date2) {
    var startDate = Date.parse(date1);
    var endDate = Date.parse(date2);
    if (startDate > endDate) {
        return 0;
    }
    if (startDate == endDate) {
        return 1;
    }
    var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000);
    return Math.ceil(days);
}

function fnDate(date) {
    var oDiv = document.getElementById("time");
    var year = date.getFullYear();//当前年份
    var month = date.getMonth();//当前月份
    var data = date.getDate();//天
    var time = year + "/" + fnW((month + 1)) + "/" + fnW(data);
    oDiv.innerHTML = time;
}

function fnW(str) {
    var num;
    str > 10 ? num = str : num = "0" + str;
    return num;
}

function fncountTime(nowdate, graduate) {
    var oDiv = document.getElementById("counttime");
    var date = new Date();
    var year = date.getFullYear();//当前年份
    var month = date.getMonth();//当前月份
    var data = date.getDate();//天
    var b = getDaysBetween(nowdate, graduate);
    var time = "距离毕业还有" + b + "天";
    oDiv.innerHTML = time;
}

$(function () {
    $('.up2 li').click(function () {
        $(this).addClass('active').siblings().removeClass('active');
        var index=$(this).index();
        $(".up4_div").eq(index).show().siblings().hide();
    })
})