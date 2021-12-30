// //查询老师选题列表
// function select_Subject_ByTeacherId() {
//     $.ajax({
//         url: 'http://localhost:8080/querySubjectByTeacherId',
//         type: 'get',
//         success: function (data) {
//             var subject = JSON.parse(data);
//             // [{"id":1,"name":"题目1","basicRequirement":"基本需求","researchObjective":"这是一个课题需求","reference":"参考文献","amount":1000,"selected":999,"passed":2,"teacherId":"t123","version":0},
//             // {"id":3,"name":"name","basicRequirement":"basicRequirement","researchObjective":"researchObjective","reference":"reference","amount":10,"selected":0,"passed":1,"teacherId":"t123","url":"url","version":0},
//             // {"id":5,"name":"课题zyz","basicRequirement":"需求","researchObjective":"目标","reference":"文献","amount":10,"selected":0,"passed":0,"teacherId":"t123","url":"D:\\Zero\\Work\\IDEA\\JavaDemo\\SpringBoot\\set-up-mutual-selection-system\\target\\classes\\files\\20211111\\0.020210918课堂复习404.docx","version":0}]
//             for(var i = 0; i < subject.length; i++) {
//                 var name = subject[i].name;
//                 //其他数据
//             }
//         }
//     })
// }