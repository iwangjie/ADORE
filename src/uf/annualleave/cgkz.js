//alert("测试！");

var yjcgje = "#field6948";//预计采购金额
var gysxz = "#field6949";//供应商性质
var zbbj = "#field6950";//招标/比价


jQuery(yjcgje).bind("propertychange", function() {

    var yjcgje_val = jQuery(yjcgje).val();
    var gysxz_val = jQuery(gysxz).val();
    //var zbbj_val = "";

    alert("yjcgje_val="+yjcgje_val);
	alert("gysxz_val="+gysxz_val);

	if (Number(yjcgje_val) >= 200000) {

		jQuery(zbbj).val(0);

	}else if(Number(yjcgje_val) < 200000&&(Number(gysxz_val)==0||Number(gysxz_val)==2)){

		jQuery(zbbj).val(1);

	}else if(Number(yjcgje_val)<200000&&Number(yjcgje_val)>=50000&&Number(gysxz_val)==1){

		jQuery(zbbj).val(1);

	}else if(Number(yjcgje_val)<50000&&Number(gysxz_val)==1){

		jQuery(zbbj).val();

	}
});
