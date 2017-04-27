/**
 * Created by adore on 2017/2/27.
 * 亲测可用，E8完美移植，分页功能以及冻结列待研究
 */
jQuery(document).ready(function () {
    floatTableHead();
});

function floatTableHead() {
    $("table[name^='oTable']").each(function (i) {
        //复制表头
        var tableid = $(this).attr("id");
        var firstTr = $("#" + tableid).find("tbody tr:eq(2)");
        var width = $("#" + tableid).width();
        var floatDiv = "<div id='floatdiv" + i + "' style='display:none;z-index: 999;position: fixed;top: 0px;'><table class=\"excelDetailTable\" id='floatTable" + i + "' width='" + width + "'><tr id='floatTr" + i + "'>" + firstTr.html();
        +"</tr></table></div>";
        $("body").append(floatDiv);
        //调整列宽
        firstTr.find("td").each(function (j) {
            var width = $(this).width();
            $("#floatTr" + i).find("td:eq(" + j + ")").width(width + 2);
        });

    });

    //动态显示
    $(window).scroll(function () {
        var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

        $("table[name^='oTable']").each(function (i) {
            var tableid = $(this).attr("id");
            var firstTr = $("#" + tableid).find("tbody tr:eq(2)");
            var lastTr = $("#" + tableid).find("tbody tr:last");
            //设置表格宽度
            $("#floatdiv" + i).css("left", $("#oTable" + i).position().left);

            if (scrollTop < firstTr.position().top) {
                $("#floatdiv" + i).hide();
            } else if (scrollTop < lastTr.position().top) {
                $("#floatdiv" + i).show();
            } else {
                $("#floatdiv" + i).hide();
            }
        });
    });
}
