var userCont;  //用户总数
var maxUserPage; //最大用户列表页数
var postPictureCont;  //套图发布列表总数
var maxPostPicturePage; //最大套图发布列表页数
var pageSize = 10;
var imageBaseUrl="http://www.chennaicha.club/gogerous";

layui.use('element', function () {
    var element = layui.element();
    element.init();
});
//用户头像更新
layui.use('upload', function () {
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#preUpdateAvatar' //绑定元素
        , url: 'user_info/pre_upadate_avatar' //上传接口
        , done: function (res) {
            //上传完毕回调
            $('#user_edit_avatar').attr("src", imageBaseUrl+res.results);
            layer.msg("上传成功");
        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});

//用户注册头像
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#registerAvatar' //绑定元素
        , url: 'user_info/pre_upadate_avatar' //上传接口
        , done: function (res) {
            //上传完毕回调
            if ('200' == res.statueCode) {
                $('#register_edit_avatar').attr("src", imageBaseUrl+res.results);
                layer.msg("上传成功");
            } else {
                layer.msg(res.message);
            }

        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});


//设置发布图片编辑封面
layui.use('upload', function () {
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#postPictureCover' //绑定元素
        , url: 'post_picture_info/pre_upadate_cover' //上传接口
        , done: function (res) {
            //上传完毕回调
            $('#post_picture_edit_cover').attr("src", imageBaseUrl+res.results);
            layer.msg("上传成功");
        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});


//发图套图的时候设置的封面
layui.use('upload', function () {
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#postPicture' //绑定元素
        , url: 'post_picture_info/pre_upadate_cover' //上传接口
        , done: function (res) {
            //上传完毕回调
            $('#post_picture_cover').attr("src", imageBaseUrl+res.results);
            layer.msg("上传成功");
        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});


//编辑面板添加图片
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#addPictureEdit' //绑定元素
        , url: 'picture/pre_post_picture' //上传接口
        , done: function (res) {
            //上传完毕回调
            layer.msg("上传成功");
            var addPistListLength = $("#pictures tr").length-1;
            var picturesTableLength=$("#pictures tr").length;

            var btnEdit = '更换';
            var btnDelete = '删除';
            var idTip = '0';
            var tr;
            tr =
                '<td id="picture_id' + addPistListLength + '">' + idTip + '</td>'
                + '<td>' + '<img id="picture_img' + addPistListLength + '" alt="套图封面" src="' + (imageBaseUrl+res.results) + '" name="cover" width="100" type="">' + '</td>'
                + '<td>' + '<input id="picture_sort' + addPistListLength + '" type="text" name="sort"  placeholder=""autocomplete="off" class="layui-input" value="' + 0 + '">' + '</td>'
                + '<td>' + '<input id="picture_file' + addPistListLength + '" type="file" value="更换" style="margin-right: 10px" onchange="changePicture(this' + "," + addPistListLength + ')">' + '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs  layui-btn-xs" onclick="deleteAddPictureInEdit(' + picturesTableLength + ')" >' + btnDelete + '</button>' + '</td>'
            $("#pictures").append('<tr>' + tr + '</tr>')
        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});

//添加套图
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#addPicture' //绑定元素
        , url: 'picture/pre_post_picture' //上传接口
        , done: function (res) {
            //上传完毕回调
            layer.msg("上传成功");
            var addPistListLength = $("#addPictureList tr").length;
            var btnEdit = '更换';
            var btnDelete = '删除';
            var tr;
            tr = '<td>' + '<img id="add_picture_img' + addPistListLength + '" alt="套图封面" src="' + res.results + '" name="cover" width="100" type="">' + '</td>'
                + '<td>' + '<input id="add_picture_sort' + addPistListLength + '" type="text" name="sort" lay-verify="required"  required placeholder=""autocomplete="off" class="layui-input" value="' + 0 + '">' + '</td>'
                + '<td>' + '<input id="add_picture_file' + addPistListLength + '" type="file" value="更换" style="margin-right: 10px" onchange="changePostPicture(this' + "," + addPistListLength + ')">' + '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs  layui-btn-xs" onclick="deleteAddPicture(' + addPistListLength + ')" >' + btnDelete + '</button>' + '</td>'
            $("#addPictureList").append('<tr>' + tr + '</tr>')
        }
        , error: function () {
            layer.msg("上传失败");
        }
    });
});


//更换添加的其中一个图片
function changePostPicture(node, imgId) {
    var formData = new FormData();
    formData.append("file", $("#add_picture_file" + imgId)[0].files[0]);
    $.ajax({
        url: "picture/pre_post_picture",
        type: 'POST',
        data: formData,
// 告诉jQuery不要去处理发送的数据
        processData: false,
// 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        success: function (data) {
            if ('200' == data.statueCode) {
                layer.msg("更换成功")
            } else {
                layer.msg("更换失败")
            }
        },
        error: function () {
            layer.msg("更换失败")
        }
    });
    var imgURL = "";
    try {
        var file = null;
        if (node.files && node.files[0]) {
            file = node.files[0];
        } else if (node.files && node.files.item(0)) {
            file = node.files.item(0);
        }
        //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径
        try {
            imgURL = file.getAsDataURL();
        } catch (e) {
            imgRUL = window.URL.createObjectURL(file);
        }
    } catch (e) {
        if (node.files && node.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                imgURL = e.target.result;
            };
            reader.readAsDataURL(node.files[0]);
        }
    }
    $('#' + 'add_picture_img' + imgId).attr('src', imgRUL);
}


function deleteAddPicture(addId) {
    $('#addPictureList tr:eq('+addId+')').remove();
}

function deleteAddPictureInEdit(addId) {
    $('#pictures tr:eq('+(addId)+')').remove();
}


layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(saveUserInfo)', function (data) {
        var userId = $('#user_edit_id').text();
        var userAvatar = $('#user_edit_avatar').attr('src');
        var nickname = $('#user_edit_nickname').val();
        var sex = $('input:radio[name="sex"]:checked').val()
        var sign = $('#user_edit_sgin').val();
        var userInfoDto = {}
        userInfoDto.id = userId;
        userInfoDto.avatar = userAvatar;
        userInfoDto.nickname = nickname;
        userInfoDto.gender = sex;
        userInfoDto.sign = sign;
        $.ajax({
            url: "user_info/update_user_info_by_admin",
            type: "post",
            data: JSON.stringify(userInfoDto),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                layer.msg(data.message);
                if ("200" == data.statueCode) {
                    setTimeout("showUserList()", 1000);
                }
            }
        })
        return false;
    });
});

//用户注册
layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(formRegister)', function (data) {
        var account = $('#register_edit_account').val();
        var password = $('#register_edit_password').val();
        var passworda = $('#register_edit_passworda').val();
        var gender = $('input:radio[name="gender"]:checked').val()
        var sign = $('#register_edit_sgin').val();
        var avatar = $('#register_edit_avatar').attr('src');
        var nickname = $('#register_edit_nickname').val();
        if (password == passworda) {
            var pwd = $.md5(password);
            var userInfoDto = {};
            userInfoDto.account = account;
            userInfoDto.avatar = avatar;
            userInfoDto.password = pwd;
            userInfoDto.nickname = nickname;
            userInfoDto.gender = gender;
            userInfoDto.sign = sign;
            $.ajax({
                url: "user_info/register_by_admin",
                type: "post",
                data: JSON.stringify(userInfoDto),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if ('200' == data.statueCode) {
                        layer.msg("添加成功");
                        setTimeout("showUserList()", 1000);
                    } else {
                        layer.msg(data.message);
                    }

                },
                error: function () {
                    layer.msg("服务异常");
                }
            })
        } else {
            layer.msg("两次密码不一致")
        }


        return false;
    });
});

function userEditReturn() {
    $("#userInfoDiv").css("display", "block");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#postPictureInfoDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
}


//显示用户列表面板
function showUserList() {
    $("#userInfoDiv").css("display", "block");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#postPictureInfoDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
    getUserCount();
    getUserList(1, pageSize);
    $('#page_num').text(1)
}

function resgiter() {
    $("#postPictureInfoDiv").css("display", "none");
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#registerEditDiv").css("display", "block");
    $("#postPictureDiv").css("display", "none");
}

//发布图片
function postPicture() {
    $("#postPictureInfoDiv").css("display", "none");
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "block");

    $('#post_picture_user_id').val("");
    $('#post_picture_cover').attr('src','splash.jpg');
     $('#post_picture_type').val("");
    $("#addPictureList  tr:not(:first)").html("");
}

function postPictureReturn() {
    $("#postPictureInfoDiv").css("display", "none");
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "block");
    $("#userInfoEditDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
}


function registerReturn() {
    $("#postPictureInfoDiv").css("display", "none");
    $("#userInfoDiv").css("display", "block");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
}

//显示用户信息编辑页
function showUserEdit(userId) {
    $("#postPictureInfoDiv").css("display", "none");
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
    getUserById(userId);
}

//根据Id获取用户信息
function getUserById(userId) {
    var param = {};
    param.userId = userId;
    $.ajax({
        url: "user_info/get_user_by_id",
        type: "get",
        data: param,
        dataType: "json",
        success: function (data) {
            $("#userInfoEditDiv").css("display", "block");
            $("#user_edit_id").text(data.results.id);
            $("#user_edit_account").text(data.results.account);
            $("#user_edit_nickname").val(data.results.nickname);
            $('#user_edit_avatar').attr("src", imageBaseUrl+data.results.avatar);
            $("#user_edit_sgin").val(data.results.sign);
            if ('1' == data.results.gender) {
                $("input[name='sex'][value='1']").prop("checked", true);
                $("input[name='sex'][value='0']").removeAttr("checked");
            } else {
                $("input[name='sex'][value='0']").prop("checked", true);
                $("input[name='sex'][value='1']").removeAttr("checked");
            }
            layui.use('form', function () {
                var from = layui.form;
                from.render('radio');
            });

        }
    })
}


//获取用户总数
function getUserCount() {
    $.ajax({
        url: "user_info/get_user_list",
        type: "get",
        dataType: "json",
        success: function (data) {
            userCont = data.results.length;
            maxUserPage = userCont / pageSize;
            if (0 != userCont % pageSize) {
                maxUserPage++;
            }
        }
    })
}

//获取用户列表，根据页数和大小
function getUserList(page, size) {
    $("#userTableList  tr:not(:first)").html("");
    var param = {};
    param.page = page;
    param.size = size;
    $.ajax({
        url: "user_info/get_user_list_by_page",
        type: "get",
        data: param,
        dataType: "json",
        success: function (data) {
            var btnEdit = '编辑';
            var btnDelete = '删除';
            for (var i = 0; i < data.results.length; i++) {
                var tr;
                tr = '<td>' + data.results[i].id + '</td>'
                    + '<td>' + data.results[i].account + '</td>'
                    + '<td>' + data.results[i].nickname + '</td>'
                    + '<td>' + data.results[i].gender + '</td>'
                    + '<td>' + data.results[i].focus + '</td>'
                    + '<td>' + data.results[i].fans + '</td>'
                    + '<td>' + '<button class="layui-btn  layui-btn-xs" onclick="showUserEdit(' + data.results[i].id + ')" >' + btnEdit + '</button>' + '<button class="layui-btn layui-btn-danger layui-btn-xs  layui-btn-xs" onclick="deleteUser(' + data.results[i].id + ')" >' + btnDelete + '</button>' + '</td>'
                $("#userTableList").append('<tr>' + tr + '</tr>')

            }

        }
    })
}


//删除用户
function deleteUser(userId) {
    var param = {};
    param.userId = userId;
    $.ajax({
        url: "user_info/delete_user",
        type: "post",
        data: param,
        dataType: "json",
        success: function (data) {
            if ("1" == data.results) {
                layer.msg("删除成功");
                setTimeout("showUserList()", 1000);
            } else {
                layer.msg("删除失败" + data.message);
            }
        }
    })
}

//用户列表上一页
$('#btn_page_pre').click(function () {
    var page = $('#page_num').text();
    if (page > 1) {
        getUserList(--page, pageSize);
        $('#page_num').text(page);
    }

});

//用户列表下一页
$('#btn_page_next').click(function () {
    var page = $('#page_num').text();
    if (userCont > 0 && page < maxUserPage - 1) {
        getUserList(++page, pageSize);
        $('#page_num').text(page);
    }

});


//下面为图片面板
//显示图片发布套图列表面板
function showPictureList() {
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "block");
    $("#userInfoEditDiv").css("display", "none");
    $("#postPictureInfoDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
    getPostPictureCount();
    getPostPictureListByPage(1, pageSize);
    $('#picture_page_num').text(1)

}


//显示发布图片信息编辑面板
function showPostPictureEditList(postPictureId) {
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "none");
    $("#userInfoEditDiv").css("display", "none");
    $("#picturesDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
    getPostPictureById(postPictureId);
}

//根据Id获取套图发布信息
function getPostPictureById(postId) {
    var param = {};
    param.postPictureId = postId;
    $.ajax({
        url: "post_picture_info/get_post_pictrue_by_id",
        type: "get",
        data: param,
        dataType: "json",
        success: function (data) {
            $("#postPictureInfoDiv").css("display", "block");
            $("#post_picture_edit_id").text(data.results.id);
            $("#post_picture_edit_user_id").text(data.results.userId);
            $("#post_picture_edit_account").text(data.results.account);
            $('#post_picture_edit_cover').attr("src", imageBaseUrl+data.results.cover);
            $("#post_picture_edit_view_num").val(data.results.viewNum);
            $("#post_picture_edit_posttype").val(data.results.postType);
            if (true == data.results.hot) {
                $("#post_picture_edit_hot").prop("checked", true)
            } else {
                $("#post_picture_edit_hot").prop("checked", false)
            }
            layui.use('form', function () {
                var from = layui.form;
                from.render('checkbox');
            });

            getPictures(data.results.id);

        }
    })
}

//发布信息编辑返回
function postPictureEditReturn() {
    $("#userInfoDiv").css("display", "none");
    $("#pictureInfoDiv").css("display", "block");
    $("#userInfoEditDiv").css("display", "none");
    $("#postPictureInfoDiv").css("display", "none");
    $("#registerEditDiv").css("display", "none");
    $("#postPictureDiv").css("display", "none");
}


//根据发布Id获取图片
function getPictures(postId) {
    $("#pictures  tr:not(:first)").html("");
    var param = {};
    param.postId = postId;
    $.ajax({
        url: "picture/get_pictures_by_postId",
        type: "get",
        data: param,
        dataType: "json",
        success: function (data) {
            //显示套图信息
            $("#picturesDiv").css("display", "block");
            var btnEdit = '更换';
            var btnDelete = '删除';
            for (var i = 0; i < data.results.length; i++) {
                var tr;
                tr =
                    '<td id="picture_id' + i + '">' + data.results[i].id + '</td>'
                    + '<td>' + '<img id="picture_img' + i + '" alt="套图封面" src="' +(imageBaseUrl+data.results[i].pictureAddress) + '" name="cover" width="100" >' + '</td>'
                    + '<td>' + '<input id="picture_sort' + i + '" type="text" name="sort" lay-verify="required"  required placeholder=""autocomplete="off" class="layui-input" value="' + data.results[i].sort + '">' + '</td>'
                    + '<td>' + '<input id="picture_file' + i + '" type="file" value="更换" style="margin-right: 10px" onchange="changePicture(this' + "," + i + ')">' + '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs  layui-btn-xs" onclick="deletePicture(' + data.results[i].id + "," + postId + ')" >' + btnDelete + '</button>' + '</td>'
                $("#pictures").append('<tr>' + tr + '</tr>')
            }

        }
    })
}

//更换套图其中一个图片
function changePicture(node, imgId) {
    var formData = new FormData();
    formData.append("file", $("#picture_file" + imgId)[0].files[0]);
    $.ajax({
        url: "picture/pre_post_picture",
        type: 'POST',
        data: formData,
// 告诉jQuery不要去处理发送的数据
        processData: false,
// 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        success: function (data) {
            if ('200' == data.statueCode) {
                $('#' + 'picture_img' + imgId).attr('src',imageBaseUrl+ data.results);
                layer.msg("更换成功")
            } else {
                layer.msg("更换失败")
            }
        },
        error: function () {
            layer.msg("更换失败")
        }
    });
    // var imgURL = "";
    // try {
    //     var file = null;
    //     if (node.files && node.files[0]) {
    //         file = node.files[0];
    //     } else if (node.files && node.files.item(0)) {
    //         file = node.files.item(0);
    //     }
    //     //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径
    //     try {
    //         imgURL = file.getAsDataURL();
    //     } catch (e) {
    //         imgRUL = window.URL.createObjectURL(file);
    //     }
    // } catch (e) {
    //     if (node.files && node.files[0]) {
    //         var reader = new FileReader();
    //         reader.onload = function (e) {
    //             imgURL = e.target.result;
    //         };
    //         reader.readAsDataURL(node.files[0]);
    //     }
    // }

}


//删除图片
function deletePicture(pictureId, postId) {
    var param = {};
    param.pictureId = pictureId;
    $.ajax({
        url: "picture/delete_picture",
        type: "post",
        data: param,
        dataType: "json",
        success: function (data) {
            if ("1" == data.results) {
                layer.msg(data.message);
                setTimeout("getPictures(" + postId + ")", 500);
            } else {
                layer.msg(data.message);
            }
        }
    })
}


//删除用户发布信息
function deletePostPicturnInfo(postPictureId) {
    var param = {};
    param.postPictureId = postPictureId;
    $.ajax({
        url: "post_picture_info/delete_post_picture_info",
        type: "post",
        data: param,
        dataType: "json",
        success: function (data) {
            if ("1" == data.results) {
                layer.msg(data.message);
                setTimeout("showPictureList()", 1000);
            } else {
                layer.msg(data.message);
            }
        }
    })
}


function getPostPictureCount() {
    $.ajax({
        url: "post_picture_info/get_post_picture_list",
        type: "get",
        dataType: "json",
        success: function (data) {
            postPictureCont = data.results.length;
            maxPostPicturePage = postPictureCont / pageSize;
            if (0 != postPictureCont % pageSize) {
                maxPostPicturePage++;
            }
        }
    })
}


function getPostPictureListByPage(page, size) {
    $("#pictureTableList  tr:not(:first)").html("");
    var param = {};
    param.page = page;
    param.size = size;
    $.ajax({
        url: "post_picture_info/get_post_picture_list_by_page",
        type: "get",
        data: param,
        dataType: "json",
        success: function (data) {
            var btnEdit = '编辑';
            var btnDelete = '删除';

            for (var i = 0; i < data.results.length; i++) {
                var tr;
                var time = getDate(data.results[i].postTime);
                tr = '<td>' + data.results[i].id + '</td>'
                    + '<td>' + data.results[i].userId + '</td>'
                    + '<td>' + data.results[i].account + '</td>'
                    + '<td>' + time + '</td>'
                    + '<td>' + data.results[i].viewNum + '</td>'
                    + '<td>' + data.results[i].likeNum + '</td>'
                    + '<td>' + data.results[i].postType + '</td>'
                    + '<td>' + data.results[i].hot + '</td>'
                    + '<td>' + '<button class="layui-btn  layui-btn-xs" onclick="showPostPictureEditList(' + data.results[i].id + ')" >' + btnEdit + '</button>' + '<button class="layui-btn layui-btn-danger layui-btn-xs  layui-btn-xs" onclick="deletePostPicturnInfo(' + data.results[i].id + ')" >' + btnDelete + '</button>' + '</td>'
                $("#pictureTableList").append('<tr>' + tr + '</tr>')

            }

        }
    })
}


//用户列表上一页
$('#btn_picture_page_pre').click(function () {
    var page = $('#picture_page_num').text();
    if (page > 1) {
        getPostPictureListByPage(--page, pageSize);
        $('#picture_page_num').text(page);
    }

});

//用户列表下一页
$('#btn_picture_page_next').click(function () {
    var page = $('#picture_page_num').text();
    if (postPictureCont > 0 && page < maxPostPicturePage - 1) {
        getPostPictureListByPage(++page, pageSize);
        $('#picture_page_num').text(page);
    }

});

//发布图片编辑保存
layui.use('form', function () {
    var form = layui.form;

    //监听提交
    form.on('submit(form_post_picture_edit)', function (data) {
        var id = $('#post_picture_edit_id').text();
        var userId = $('#post_picture_edit_user_id').text();
        var cover = $('#post_picture_edit_cover').attr('src');
        var viewNum = $('#post_picture_edit_view_num').val();
        var ishot = $("input[type='checkbox']").is(':checked') ? false : true;
        var postType = $('#post_picture_edit_posttype').val();
        var postPictureDto = {}
        postPictureDto.id = id;
        postPictureDto.userId = userId;
        postPictureDto.viewNum = viewNum;
        postPictureDto.postType = postType;
        postPictureDto.cover = cover;
        postPictureDto.hot = ishot;
        $.ajax({
            url: "post_picture_info/update_post_picture_info",
            type: "post",
            data: JSON.stringify(postPictureDto),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if ('200' == data.statueCode) {
                    updateOrPostPicture(id)
                } else {
                    layer.msg(data.message + "--" + data.statueCode);
                }
            }
        })
        return false;
    });
});

//更新或者发布图片
function updateOrPostPicture(postId) {
    var picturesLength = $("#pictures tr").length;
    var pictureDtos = [];
    for (var i = 0; i < picturesLength - 1; i++) {
        var pictureId = $('#picture_id' + i).text()
        var sort = $('#picture_sort' + i).val()
        var pictureAddress = $('#picture_img' + i).attr("src")
        var objectData = {
            id: pictureId,
            postPictureInfoId: postId,
            sort: sort,
            pictureAddress: pictureAddress,
        };
        pictureDtos.push(objectData);
    }
    $.ajax({
        type: "POST",
        url: 'picture/post_picture',
        data: JSON.stringify(pictureDtos),
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            layer.msg(data.message)
            if ('200' == data.statueCode) {
                setTimeout("showPictureList()", 1000);
            }
        },
        error: function () {
            layer.msg("操作异常")
        }
    });

}


//发布图片-添加发布
layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(form_post_picture)', function (data) {
        var userId = $('#post_picture_user_id').val();
        var cover = $('#post_picture_cover').attr('src');
        var ishot = $("input[type='checkbox']").is(':checked') ? true : false;
        var postType = $('#post_picture_type').val();
        var postPictureDto = {}
        postPictureDto.userId = userId;
        postPictureDto.postType = postType;
        postPictureDto.cover = cover;
        postPictureDto.hot = ishot;
        $.ajax({
            url: "post_picture_info/post",
            type: "post",
            data: JSON.stringify(postPictureDto),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if ('200' == data.statueCode) {
                     postAddPicture(data.results)
                } else {
                    layer.msg(data.message + "--" + data.statueCode);
                }
            }
        })
        return false;
    });
});

//发布图片的时候添加的图片
function postAddPicture(postId) {
    var addPistListLength = $("#addPictureList tr").length;
    var pictureDtos = [];
    for (var i = 1; i <addPistListLength ; i++) {
        var sort = $('#add_picture_sort' + i).val()
        var pictureAddress = $('#add_picture_img' + i).attr("src")
        var objectData = {
            id:0,
            postPictureInfoId: postId,
            sort: sort,
            pictureAddress: pictureAddress,
        };
        pictureDtos.push(objectData);
    }
    $.ajax({
        type: "POST",
        url: 'picture/post_picture',
        data: JSON.stringify(pictureDtos),
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            if ('200' == data.statueCode) {
                layer.msg(data.message)
                setTimeout("showPictureList()", 1000);
            } else {
                layer.msg(data.message)
            }
        },
        error: function () {
            layer.msg("操作异常")
        }
    });

}



//毫秒转日期
function getDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

