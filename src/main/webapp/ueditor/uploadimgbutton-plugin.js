UE.registerUI('uploadimg_btn',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
        	ue.execCommand('inserthtml', '<span class="chooshine-placeholder"></span>');
        	openUploadDialog();
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:"图片上传",
        //提示
        title:"图片上传",
        //需要添加的额外样式，指定icon图标
        cssRules :'background-position: -380px 0px;',
        //点击时执行的命令
        onclick:function () {
        	ue.execCommand('inserthtml', '<span class="chooshine-placeholder"></span>');
        	openUploadDialog();
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
});