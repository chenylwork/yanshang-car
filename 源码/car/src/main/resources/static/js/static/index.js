$(function () {
    pageSearchAccount();
    pagintor();
    $("#search_btn").click(function () {
        pageSearchAccount();
    });
});
function pageSearchAccount() {
    searchAccount($("#pageNo").val(),$("#length").val());
}

/**
 * 数据列表点击方法
 * @param id
 */
function infoClickFunction(id) {
    window.location.href = url_m.prefic+"/info.html?id="+id;
}
// 获取数据并填充
function searchAccount(pageNo,length) {
    var params = {
        "account":$("#mobile").val(),
        "name":$("#name").val(),
        "pid":$("#pid").val(),
        "status":$("#status").val(),
        "no":pageNo,
        "length":length,
        "use":1
    };
    console.info("查询参数为：");
    console.info(params)
    $.ajax({
        url:url_m.prefic+"/account/info/search",
        data:params,
        success:function(data){
            $("#table-box").empty();
            if ((data.status == "1" || data.status == 1) && data.data.size > 0) {
                var initHtml = "<table class=\"table table-bordered table-striped\">";
                initHtml += "<thead>" +
                    "<tr>" +
                    "<th>手机</th>\n" +
                    "<th>姓名</th>\n" +
                    "<th>借款金额</th>\n" +
                    "<th>身份证</th>\n" +
                    "<th>银行卡</th>\n" +
                    "<th>付款方式</th>\n" +
                    "<th>年收入</th>\n" +
                    "<th>工作年限</th>\n" +
                    "<th>有房</th>\n" +
                    "<th>有车</th>\n" +
                    "<th>信用卡</th>\n" +
                    "<th>信用卡号</th>\n" +
                    "<th>信用卡开户行</th>\n" +
                    "<th>申请时间</th>\n" +
                    "<th>信息状态</th>\n" +
                    "<th>操作</th>\n" +
                    "</tr>\n" +
                    "</thead>";
                initHtml += "<tbody>";
                /***************数据部分****************/
                var dataArray = data.data.data;
                for (var i in dataArray) {
                    var status = dataArray[i].status;
                    // if (status == 3 || status == "3") break;
                    var loan = "";
                    switch (dataArray[i].loan){
                        case "1" :
                            loan = "有房贷";
                            break;
                        case "2":
                            loan = "无房贷";
                            break;
                        case "0":
                            loan = "有房无贷";
                            break;
                    }
                    initHtml += "<tr class='info_tr'>\n" +
                        "<td>"+dataArray[i].account+"</td>\n" +
                        "<td>"+(dataArray[i].name == null ? "待录入" : dataArray[i].name)+"</td>\n" +
                        "<td>"+(dataArray[i].borrow == null ? "待录入" : dataArray[i].borrow)+"</td>\n" +
                        "<td>"+(dataArray[i].pid == null ? "待录入" : dataArray[i].pid)+"</td>\n" +
                        "<td>"+(dataArray[i].cardCode == null ? "待录入" : dataArray[i].cardCode)+"</td>\n" +
                        "<td>"+(dataArray[i].payment == null || dataArray[i].payment == 0 || dataArray[i].payment == '0' ? "待录入" : dataArray[i].payment)+"</td>\n" +
                        "<td>"+(dataArray[i].income == null? "待录入" : dataArray[i].income)+"</td>\n" +
                        "<td>"+(dataArray[i].workTime == null? "待录入" : dataArray[i].workTime)+"</td>\n" +
                        ////////////
                        "<td>"+(dataArray[i].house == null? "待录入" : dataArray[i].house)+"</td>\n" +
                        "<td>"+(dataArray[i].car == null? "待录入" : dataArray[i].car)+"</td>\n" +
                        "<td>"+(dataArray[i].creditCard == null? "待录入" : dataArray[i].creditCard)+"</td>\n" +
                        "<td>"+(dataArray[i].creditCardNo == null? "无" : dataArray[i].creditCardNo)+"</td>\n" +
                        "<td>"+(dataArray[i].creditCardBank == null? "无" : dataArray[i].creditCardBank)+"</td>\n" +
                        /////////////
                        "<td>"+(dataArray[i].openTime == null? "待录入" : dataArray[i].openTime)+"</td>"+
                        "<td>\n" +
                        $("#status_"+status).prop("outerHTML") +
                        "</td>\n" +
                        "<td onclick='infoClickFunction("+dataArray[i].infoID+")'><a href=\"#\" class=\"btn btn-info btn-hover btn-sm\">\n" +
                        "操作" +
                        "\t\t\t</a></td>"+
                        "</tr>";
                }
                /****************数据部分***************/
                initHtml += "</tbody>";
                initHtml += "</div>";
                $("#message").text("");
                $("#table-box").append(initHtml);
                console.log(data);
                pagintor(data.data.no,data.data.pages);
            } else {
                pagintor(1,1);
                $("#message").text(data.content);
            }
        },
    });
}

/**
 * 分页处理
 */
function pagintor(pageNo,pages) {
    $("#pageLimit").empty();
    $('#pageLimit').bootstrapPaginator({
        currentPage: pageNo,//当前的请求页面。
        totalPages: pages,//一共多少页。
        size: "normal",//应该是页眉的大小。
        bootstrapMajorVersion: 3,//bootstrap的版本要求。
        alignment: "right",
        numberOfPages: 5,//一页列出多少数据。
        itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page){//给每个页眉绑定一个事件，其实就是ajax请求，其中page变量为当前点击的页上的数字。
            searchAccount(page,$("#length").val());
        }
    });
}
