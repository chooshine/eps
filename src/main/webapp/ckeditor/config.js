/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	config.language = 'zh-cn';
//	config.uiColor = '#AADC6E';
	// config.width = 'auto'; // 宽度  
   // config.height = '300px'; // 高度  
    config.skin = 'kama';//界面v2,kama,office2003  
    config.toolbar = 'Full';// 工具栏风格Full,Basic 
    config.toolbarCanCollapse = true;
    config.filebrowserBrowseUrl = '/corpus/ckfinder/ckfinder.html'; //不要写成"~/ckfinder/..."或者"/ckfinder/..."
    config.filebrowserImageBrowseUrl = '/corpus/ckfinder/ckfinder.html?type=Images';
    config.filebrowserFlashBrowseUrl = '/corpus/ckfinder/ckfinder.html?type=Flash';
    config.filebrowserUploadUrl = '/corpus/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '/corpus/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = '/corpus/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'; 
};
