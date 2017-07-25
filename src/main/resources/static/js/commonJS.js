/**
 * Created by kasimodo on 2017-7-7.
 */

function formToJson(formObj) {
    var o = {};
    var a = formObj.serializeArray();
    $.each(a, function () {

        if (this.value) {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || null);
            } else {
                if ($("[name='" + this.name + "']:checkbox", formObj).length) {
                    o[this.name] = [this.value];
                } else if (typeof($("select[name=" + this.name + "]").attr("multiple")) != "undefined") {
                    o[this.name] = [this.value];
                } else if (typeof($("input[name=" + this.name + "]").attr("multiple")) != "undefined") {
                    o[this.name] = [this.value];
                }
                else {
                    o[this.name] = this.value || null;
                }
            }
        }
    });
    return o;
};

// 日期格式化
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


function showAddSuccessNotify() {
    $.notify({
        // options
        icon: 'fa fa-check',
        message: '新增成功！'
    }, {
        // settings
        type: 'success',
        animate: {
            enter: 'animated fadeInRight',
            exit: 'animated fadeOutRight'
        }
    });
}

function showEditSuccessNotify() {
    $.notify({
        // options
        icon: 'fa fa-check',
        message: '修改成功！'
    }, {
        // settings
        type: 'success',
        animate: {
            enter: 'animated fadeInRight',
            exit: 'animated fadeOutRight'
        }
    });
}

function showErrorNotify(msg) {
    $.notify({
        // options
        icon: 'fa fa-exclamation',
        message: msg
    }, {
        // settings
        type: 'danger',
        animate: {
            enter: 'animated fadeInRight',
            exit: 'animated fadeOutRight'
        }
    });
}

function reValidateField(formId, thisId) {
    $('#' + formId).bootstrapValidator('revalidateField', thisId);
}

