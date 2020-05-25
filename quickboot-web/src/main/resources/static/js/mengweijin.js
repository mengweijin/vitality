const mengweijin = {
    /**
     * @params dataList List<Object>或者List<Map<String, Object>>数据
     * @params rootId 根值，树图或者级联数据最顶层的数据的id
     */
    buildTreeData: function (dataList, rootId){
        let itemArr = [];
        for(let i = 0; i < dataList.length; i++){
            let node = dataList[i];
            if(node.parentId === rootId){
                let newNode = {id : node.id, name : node.name, children : mwj.buildTreeData(dataList, node.id)};
                itemArr.push(newNode);
            }
        }
        return itemArr;
    }
};
