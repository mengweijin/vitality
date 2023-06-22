layui.define(['jquery'], function(exports) {
	"use strict";

	var MOD_NAME = 'vtlConfig';
	var $ = layui.jquery;

    var vtlConfig = {
        getByCode: function(code) {
            let config = null;
            $.ajax({
                url: '/vtl-config/byCode/' + code,
                async: false,
                method: 'get',
                success: function(result) {
                    config = result;
                }
            });
            return config;
        },
        getValByCode: function(code) {
            let config = this.getByCode(code);
            if(config) {
                return config.val;
            }
            return null;
        }

    };

	exports(MOD_NAME, vtlConfig);
})