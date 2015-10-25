'use strict';
angular
.module('app')
.service('sharedProperties', sharedProperties);

function sharedProperties(){
	
	var user = null;
	var that = this;
	
    return {
        getUser: function () {
            return that.user;
        },
        setUser: function(user) {
        	that.user = user;
        }
    };
	
}
