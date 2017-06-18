
function Map() {
	this.elements = new Array();
	
	//获取MAP元素个数
	this.size = function () {
		return this.elements.length;
	};
	
	//判断MAP是否为空
	this.isEmpty = function () {
		return (this.elements.length < 1);
	};
	
	//删除MAP所有元素
	this.clear = function () {
		this.elements = new Array();
	};
	
	//向MAP中增加元素（key, value) 
	this.put = function (_key, _value) {
		this.elements.push({key:_key, value:_value});
	};
	
	//删除指定KEY的元素，成功返回True，失败返回False
	this.removeByKey = function (_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//删除指定VALUE的元素，成功返回True，失败返回False
	this.removeByValue = function (_value) {//removeByValueAndKey
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//删除指定VALUE的元素，成功返回True，失败返回False
	this.removeByValueAndKey = function (_key, _value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value && this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//获取指定KEY的元素值VALUE，失败返回NULL
	this.get = function (_key) {
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		}
		catch (e) {
			return false;
		}
		return false;
	};
	
	//获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
	this.element = function (_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	};
	
	//判断MAP中是否含有指定KEY的元素
	this.containsKey = function (_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//判断MAP中是否含有指定VALUE的元素
	this.containsValue = function (_value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//判断MAP中是否含有指定VALUE的元素
	this.containsObj = function (_key, _value) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value && this.elements[i].key == _key) {
					bln = true;
				}
			}
		}
		catch (e) {
			bln = false;
		}
		return bln;
	};
	
	//获取MAP中所有VALUE的数组（ARRAY）
	this.values = function () {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	};
	
	//获取MAP中所有VALUE的数组（ARRAY）
	this.valuesByKey = function (_key) {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			if (this.elements[i].key == _key) {
				arr.push(this.elements[i].value);
			}
		}
		return arr;
	};
	
	//获取MAP中所有KEY的数组（ARRAY）
	this.keys = function () {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	};
	
	//获取key通过value
	this.keysByValue = function (_value) {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			if (_value == this.elements[i].value) {
				arr.push(this.elements[i].key);
			}
		}
		return arr;
	};
	
	//获取MAP中所有KEY的数组（ARRAY）
	this.keysRemoveDuplicate = function () {
		var arr = new Array();
		for (i = 0; i < this.elements.length; i++) {
			var flag = true;
			for (var j = 0; j < arr.length; j++) {
				if (arr[j] == this.elements[i].key) {
					flag = false;
					break;
				}
			}
			if (flag) {
				arr.push(this.elements[i].key);
			}
		}
		return arr;
	};
	
	this.toJson=function(){
		var str="[";
		for(var i=0;i<this.elements.length;i++){
			str+="{name:\""+this.elements[i].key+"\",value:\""+this.elements[i].value+"\"},";
		}
		return str.substring(0,str.length-1)+"]";
	};
	
	
	this.toURL=function(){
		var str="";
		for(var i=0;i<this.elements.length;i++){
			str+=this.elements[i].key+"="+this.elements[i].value+"&";
		}
		return str.substring(0,str.length-1);
	};
}
