import com.alibaba.dme.bcp.ump.api.constant.common.ProductType;

if(PRODUCT_TYPE == ProductType.TICKET){
    function_mark_promotion();
    actionExecute(ACTION_TYPE);
    func_agentFee();
    func_serviceFee();
    func_subsidy();
}

if(PRODUCT_TYPE == ProductType.GOODS){
   function_mark_promotion();
   actionExecute(ACTION_TYPE);
   func_agentFee();
   func_serviceFee();
   func_subsidy();
}


function actionExecute(String ACTION_TYPE){
    if(ACTION_TYPE=="REDUCE"){
        func_reduce;
    }

    if(ACTION_TYPE=="DISCOUNT"){
        func_discount;
    }

    if(ACTION_TYPE=="REDUCE_TO"){
        func_reduceTo;
    }

    if(ACTION_TYPE=="LOWEST_PLUS"){
        func_lowestPlus;
    }
}


