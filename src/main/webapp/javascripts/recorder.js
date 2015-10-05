
(function(global) {
  var Recorder;

  var RECORDED_AUDIO_TYPE = "audio/wav";

  Recorder = {
    recorder: null,
    recorderOriginalWidth: 0,
    recorderOriginalHeight: 0,
    uploadFormId: null,
    uploadFieldName: null,
    isReady: false,

    connect: function(name, attempts) {
      if(navigator.appName.indexOf("Microsoft") != -1) {
        Recorder.recorder = window[name];
      } else {
        Recorder.recorder = document[name];
      }

      if(attempts >= 40) {
        return;
      }

      // flash app needs time to load and initialize
      if(Recorder.recorder && Recorder.recorder.init) {
        Recorder.recorderOriginalWidth = Recorder.recorder.width;
        Recorder.recorderOriginalHeight = Recorder.recorder.height;
        if(Recorder.uploadFormId && $) {
          var frm = $(Recorder.uploadFormId); 
          Recorder.recorder.init(frm.attr('action').toString(), Recorder.uploadFieldName, frm.serializeArray());
        }
        return;
      }

      setTimeout(function() {Recorder.connect(name, attempts+1);}, 100);
    },

    playBack: function(name) {
      Recorder.recorder.playBack(name);
    },

    pausePlayBack: function(name) {
      Recorder.recorder.pausePlayBack(name);
    },
    
    playBackFrom: function(name, time) {
      Recorder.recorder.playBackFrom(name, time);
    },

    record: function(name, filename) {
      Recorder.recorder.record(name, filename);
    },

    stopRecording: function() {
      Recorder.recorder.stopRecording();
    },

    stopPlayBack: function() {
      Recorder.recorder.stopPlayBack();
    },

    observeLevel: function() {
      Recorder.recorder.observeLevel();
    },

    stopObservingLevel: function() {
      Recorder.recorder.stopObservingLevel();
    },

    observeSamples: function() {
      Recorder.recorder.observeSamples();
    },
    saveRecorde:function(name){
      Recorder.recorder.save(name);
    },
    stopObservingSamples: function() {
      Recorder.recorder.stopObservingSamples();
    },

    resize: function(width, height) {
      Recorder.recorder.width = width + "px";
      Recorder.recorder.height = height + "px";
    },

    defaultSize: function() {
      Recorder.resize(Recorder.recorderOriginalWidth, Recorder.recorderOriginalHeight);
    },

    show: function() {
      Recorder.recorder.show();
    },

    hide: function() {
      Recorder.recorder.hide();
    },

    duration: function(name) {
      return Recorder.recorder.duration(name || Recorder.uploadFieldName);
    },

    getBase64: function(name) {
      var data = Recorder.recorder.getBase64(name);
      return 'data:' + RECORDED_AUDIO_TYPE + ';base64,' + data;
    },

    getBlob: function(name) {
      var base64Data = Recorder.getBase64(name).split(',')[1];
      return base64toBlob(base64Data, RECORDED_AUDIO_TYPE);
    },

    getCurrentTime: function(name) {
    	return Recorder.recorder.getCurrentTime(name);
    },

    isMicrophoneAccessible: function() {
      return Recorder.recorder.isMicrophoneAccessible();
    },

    updateForm: function() {
      var frm = $(Recorder.uploadFormId); 
      Recorder.recorder.update(frm.serializeArray());
    },

    showPermissionWindow: function(options) {
      Recorder.resize(240, 160);
      // need to wait until app is resized before displaying permissions screen
      var permissionCommand = function() {
        if (options && options.permanent) {
          Recorder.recorder.permitPermanently();
        } else {
          Recorder.recorder.permit();
        }
      };
      setTimeout(permissionCommand, 1);
    },

    configure: function(rate, gain, silenceLevel, silenceTimeout) {
      rate = parseInt(rate || 22);
      gain = parseInt(gain || 100);
      silenceLevel = parseInt(silenceLevel || 0);
      silenceTimeout = parseInt(silenceTimeout || 4000);
      switch(rate) {
      case 44:
      case 22:
      case 11:
      case 8:
      case 5:
        break;
      default:
        throw("invalid rate " + rate);
      }

      if(gain < 0 || gain > 100) {
        throw("invalid gain " + gain);
      }

      if(silenceLevel < 0 || silenceLevel > 100) {
        throw("invalid silenceLevel " + silenceLevel);
      }

      if(silenceTimeout < -1) {
        throw("invalid silenceTimeout " + silenceTimeout);
      }

      Recorder.recorder.configure(rate, gain, silenceLevel, silenceTimeout);
    },

    setUseEchoSuppression: function(val) {
      if(typeof(val) != 'boolean') {
        throw("invalid value for setting echo suppression, val: " + val);
      }

      Recorder.recorder.setUseEchoSuppression(val);
    },

    setLoopBack: function(val) {
      if(typeof(val) != 'boolean') {
        throw("invalid value for setting loop back, val: " + val);
      }

      Recorder.recorder.setLoopBack(val);
    }
  };

  function base64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    var byteCharacters = atob(b64Data);
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      var slice = byteCharacters.slice(offset, offset + sliceSize);

      var byteNumbers = new Array(slice.length);
      for (var i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      var byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, {type: contentType});
  }


  global.FWRecorder = Recorder;
  var Compress = {
		compress:function(s){
			/*var strCompressedString = "";
		    var ht = new Array();
		    for(i = 0; i < 128; i++) {
		        ht[i] = i;
		    }
		    var used = 128;
		    var intLeftOver = 0;
		    var intOutputCode = 0;
		    var pcode = 0;
		    var ccode = 0;
		    var k = 0;

		    for(var i=0; i<strNormalString.length; i++) {
		        ccode = strNormalString.charCodeAt(i);
		        k = (pcode << 8) | ccode;
		        if(ht[k] != null) {
		            pcode = ht[k];
		        } else {
		            intLeftOver += 12;
		            intOutputCode <<= 12;
		            intOutputCode |= pcode;
		            pcode = ccode;
		            if(intLeftOver >= 16) {
		                strCompressedString += String.fromCharCode( intOutputCode >> ( intLeftOver - 16 ) );
		                intOutputCode &= (Math.pow(2, (intLeftOver - 16)) - 1);
		                intLeftOver -= 16;
		            }
		            if(used < 4096) {
		                used ++;
		                ht[k] = used - 1;
		            }
		        }
		    }

		    if(pcode != 0) {
		        intLeftOver += 12;
		        intOutputCode <<= 12;
		        intOutputCode |= pcode;
		    }

		    if(intLeftOver >= 16) {
		        strCompressedString += String.fromCharCode( intOutputCode >> ( intLeftOver - 16 ) );
		        intOutputCode &= (Math.pow(2,(intLeftOver - 16)) - 1);
		        intLeftOver -= 16;
		    }

		    if( intLeftOver > 0) {
		        intOutputCode <<= (16 - intLeftOver);
		        strCompressedString += String.fromCharCode( intOutputCode );
		    }

		    return strCompressedString;*/
			var dict = {};
		    var data = (s + "").split("");
		    var out = [];
		    var currChar;
		    var phrase = data[0];
		    var code = 256;
		    for (var i=1; i<data.length; i++) {
		        currChar=data[i];
		        if (dict['_' + phrase + currChar] != null) {
		            phrase += currChar;
		        }
		        else {
		            out.push(phrase.length > 1 ? dict['_'+phrase] : phrase.charCodeAt(0));
		            dict['_' + phrase + currChar] = code;
		            code++;
		            phrase=currChar;
		        }
		    }
		    out.push(phrase.length > 1 ? dict['_'+phrase] : phrase.charCodeAt(0));
		    for (var i=0; i<out.length; i++) {
		        out[i] = String.fromCharCode(out[i]);
		    }
		    return out.join("");
		},
		deCompress:function Decompress(s) {
		    /*var strNormalString = "";
		    var ht = new Array();

		    for(i = 0; i < 128; i++) {
		        ht[i] = String.fromCharCode(i);
		    }

		    var used = 128;
		    var intLeftOver = 0;
		    var intOutputCode = 0;
		    var ccode = 0;
		    var pcode = 0;
		    var key = 0;

		    for(var i=0; i<strCompressedString.length; i++) {
		        intLeftOver += 16;
		        intOutputCode <<= 16;
		        intOutputCode |= strCompressedString.charCodeAt(i);

		        while(1) {
		            if(intLeftOver >= 12) {
		                ccode = intOutputCode >> (intLeftOver - 12);
		                if( typeof( key = ht[ccode] ) != "undefined" ) {
		                     strNormalString += key;
		                    if(used > 128) {
		                        ht[ht.length] = ht[pcode] + key.substr(0, 1);
		                    }
		                     pcode = ccode;
		                } else {
		                    key = ht[pcode] + ht[pcode].substr(0, 1);
		                    strNormalString += key;
		                    ht[ht.length] = ht[pcode] + key.substr(0, 1);
		                    pcode = ht.length - 1;
		                }

		                used ++;
		                intLeftOver -= 12;
		                intOutputCode &= (Math.pow(2,intLeftOver) - 1);
		            } else {
		                break;
		            }
		        }
		    }
		    return strNormalString;*/
			var dict = {};
		    var data = (s + "").split("");
		    var currChar = data[0];
		    var oldPhrase = currChar;
		    var out = [currChar];
		    var code = 256;
		    var phrase;
		    for (var i=1; i<data.length; i++) {
		        var currCode = data[i].charCodeAt(0);
		        if (currCode < 256) {
		            phrase = data[i];
		        }
		        else {
		           phrase = dict['_'+currCode] ? dict['_'+currCode] : (oldPhrase + currChar);
		        }
		        out.push(phrase);
		        currChar = phrase.charAt(0);
		        dict['_'+code] = oldPhrase + currChar;
		        code++;
		        oldPhrase = phrase;
		    }
		    return out.join("");
		}
		  
  };
  global.zipStr = Compress;

})(this);
(function($){
	var cfg = {
		id:"_audio",
		savePath:'',
		recordHandler:function(){},
	 	stopHandler:function(){},
	 	playHandler:function(){},
	 	saveHandler:function(){},
	};
	$.fn.extend({
		wavRecorder : function(c){
			var _config = {};
			$.extend(_config,cfg);
			var _this = $(this);
			$.extend(_config,c);
			var _recorder_container,_recorder, _startRecorder,_stopRecorder,_playStatus;
			var _playBack,_pause,_save,_level,_playbar, _delete, _pauseTime, _totalTime;
			createHtml();
			addHandler();
			var _filePath = "";
			function createHtml(){
				_recorder_container = $("<section class='recorder-container' id='"+_config.id+"'></section>");
				_recorder = $("<div class='recorder'></div>");
				_playStatus = $('<input type="hidden" id="'+_config.id+'_playing" value="-1">');
				_pauseTime = $('<input type="hidden" id="'+_config.id+'_pause" value="0">');
				_totalTime = $('<input type="hidden" id="'+_config.id+'_total" value="0">');
				_recorder_container.append(_recorder)
								   .append(_playStatus)
								   .append(_pauseTime)
								   .append(_totalTime);
				//_startRecorder = $("<button class='start-recording'><img src='../images/record.png' alt='Record'></button>");
				_startRecorder = $("<button class='start-recording' title='开始录音'></button>").hide();
				//_stopRecorder = $('<button class="stop-recording"><img src="../images/stop.png" alt="Stop Recording"/></button>').hide();
				_stopRecorder = $('<button class="stop-recording" title="停止录音"></button>').hide();
				//_playBack = $('<button class="start-playing"><img src="../images/play.png" alt="Play"/></button>').hide();
				_playBack = $('<button class="start-playing"></button>').hide();
				//_pause = $('<button class="pause"><img src="../images/pause.png" alt="pause"/></button>').hide();
				_pause = $('<button class="pause" title="暂停录音"></button>').hide();
				//_time = $('<span class="time"><em>0:00</em>/3:00</span>').hide();
				_level = $('<div class="level" title="录音状态"><div class="progress"></div></div>').hide();
				_save = $('<button class="save">save</button>').hide();
				_playbar = $('<div class="mbar"><div class="tbar"><div class="pbar"></div></div><span class="time"><em>0:00</em>3:00</span></div>').hide();
				//_delete = $('<button class="delete"><img src="../images/delete.png" alt="delete"/></button>').hide();
				//_delete = $('<button class="delete"></button>').hide();
				_delete = $('<button class="delete"></button>').hide();
				_recorder.append(_startRecorder)
						 .append(_playBack)
						 .append(_pause)
						 .append(_playbar)
				         .append(_stopRecorder)
				         .append(_delete)
				         .append(_level);
				_this.append(_recorder_container);
			};
			function playing(time){
				if(_playStatus.val() != -1){
					_recorder.find('.pbar').css({width:time/1000/_totalTime.val()*100+'%'});
					if(time / 1000 >= _totalTime.val()){
						time = _totalTime.val()*1000;
					}else{
						setTimeout(function(){playing(time+500);},500);
					}
					var m = Math.floor(time/1000/60);
					var s = Math.round(time/1000%60);
					_pauseTime.val(time+500);
					_playbar.find('.time em').text(m+":"+(s<10?'0'+s : s));
					
				}
			}
			function reset(){
				//_startRecorder.show();
				_stopRecorder.hide();
				_pause.hide();
				_level.hide();
				_playBack.hide();
				_playbar.hide();
				_delete.hide();
				_playbar.find('.pbar').css({width:0});
				_playbar.find('.tbar').css({width:0});
				_playbar.find('.time').html('<em>0:00</em>3:00');
			}
			function upload(data){
				//var _data = {wavData:data.split(',')[1]};
				data = data.split(',')[1];
				/*console.log(data.length);
				var ndata = zipStr.compress(data);
				//console.log(ndata);
				console.log(ndata.length);
				var ldata = zipStr.deCompress(ndata);
				console.log(ldata.length);*/
				var basePath = appPath;
				if(appPath.lastIndexOf("/") == (appPath.length-1)) {
					basePath = appPath.substring(0, appPath.length-1);
				}
				$.ajax({
					url:basePath+"/wav/uploadBase64.json",
					type:'POST',
		  			data:data,
		  			success:function(data){
		  				_filePath = data.path;
		  				if(_config.saveHandler)_config.saveHandler(data);
		  			}
				});
			}
			function addHandler(){
				if(_recorder_container){
					_recorder_container.mouseenter(function(e){
						//if(_recorder.find('.upload').length<1)0
						//	_recorder.append($(".upload"));
					});
				}
				if(_startRecorder){
					_startRecorder.click(function(){
						if(!microphoneConnected){
							FWRecorder.showPermissionWindow();
						}else{
							if(isPlaying)return;
							FWRecorder.record(_config.id,_config.id+".wav");
							_startRecorder.hide();
							_pause.show();
							_level.show();
							_playbar.show();
							//_time.show();
							_stopRecorder.show();
						}
						
					});
				}
				if(_stopRecorder){
					_stopRecorder.click(function(){
						if(_config.stopHandler)_config.stopHandler();
						/*_playBack.show();
						_playbar.show();
						_delete.show();*/
						_level.hide();
						_startRecorder.hide();
						_stopRecorder.hide();
						_pause.hide();
						//_time.hide();
						FWRecorder.stopRecording();
						upload(FWRecorder.getBase64(_config.id));
					});
				}
				if(_playBack){
					_playBack.click(function(){
						if(_config.playHandler)_config.playHandler();
						if(isPlaying) return;
						_playBack.hide();
						_pause.show();
						_stopRecorder.hide();
						FWRecorder.playBack(_config.id);
						_playStatus.val(1);
						//_datalen.val();
						playing(parseInt(_pauseTime.val()));
					});
				}
				if(_pause){
					_pause.click(function(){
						_pause.hide();
						if(_playStatus.val() != -1){
							_playBack.show();
							_playStatus.val(-1);
						}else{
							_level.find('.progress').css({height:0});
							_startRecorder.show();
							//_stopRecorder.hide();
						}
						FWRecorder.pausePlayBack(_config.id);
						
					});
				}
				if(_delete){
					_delete.click(function(){
						reset();
						var basePath = appPath;
						if(appPath.lastIndexOf("/") == (appPath.length-1)) {
							basePath = appPath.substring(0, appPath.length-1);
						}
						$.ajax({
							url:basePath+"/wav/deleteWav.json",
							type:'GET',
				  			data:{path:_filePath},
				  			success:function(data){
				  				//alert(data);
				  			}
						});
					});
				}
			}
		}
	});
})(jQuery);
$('body').append('<div class="upload" style="position:fixed;top:40%;left:40%;width:0;height:0;z-index:1000;"><div id="flashcontent"></div></div>');
var RECORDER_APP_ID = "recorderApp";
var appWidth = 1;
var appHeight = 1;
var flashvars = {};
var params = {};
var attributes = {'id': RECORDER_APP_ID, 'name': RECORDER_APP_ID};
swfobject.embedSWF(appPath + "javascripts/Recorder.swf", "flashcontent", appWidth, appHeight, "11.0.0", "", flashvars, params, attributes);

var microphoneConnected = false;
var isPlaying = false;
window.fwr_event_handler = function fwr_event_handler() {
	 var name, $controls;
    switch (arguments[0]) {
     	case "ready":
     		FWRecorder.uploadFormId = "#wav_uploadForm";
     		FWRecorder.uploadFieldName = "upload_file[filename]";
     		FWRecorder.connect(RECORDER_APP_ID, 0);
     		FWRecorder.recorderOriginalWidth = appWidth;
     		FWRecorder.recorderOriginalHeight = appHeight;
     		break;
     	case "microphone_user_request" :
     		FWRecorder.showPermissionWindow();
     		break;
     	case "permission_panel_closed":
           FWRecorder.defaultSize();
           break;
     	case "microphone_connected":
     		microphoneConnected = true;
     		break;
     	case "recording":
           FWRecorder.hide();
           FWRecorder.observeLevel();
           var name = arguments[1];
           isPlaying = true;
           break;
     	case "microphone_activity":
     		var name = arguments[1];
     		break;
     	case "recording_stopped":
           //FWRecorder.show();
           FWRecorder.stopObservingLevel();
           var name = arguments[1];
           isPlaying = false;
           /*$('#'+name).find('.level .progress').css({height: 0});
           $('#'+name).find('.mbar .tbar').css({width:'100%'});
           $('#'+name).find('.mbar .pbar').css({width:0});
           var len = arguments[2].toFixed(0);
           //console.log(len);
     	   var m = Math.floor(len/60);
     	   var s = Math.round(len%60);
     	   $('#'+name+'_total').val(len);
           $('#'+name).find('.mbar .time').html('<em>0:00</em>/' +  m + ":" + (s < 10?('0'+s):s));*/
           break;
     	case "microphone_level":  //声音大小
     	   var name = arguments[1];
           $('#'+name).find('.level .progress').css({height: arguments[2] * 100 + '%'});
     	   var len = arguments[3].toFixed(0);
     	   var m = Math.floor(len/60);
     	   var s = Math.round(len%60);
     	  $('#'+name).find('.mbar .tbar').css({width:arguments[3]/180*100 + '%'});
     	  $('#'+name).find('.mbar .time').html('<em>'+ m + ":" + (s < 10?('0'+s):s)+'</em>3:00');
     	  if(m>=3){
     		 FWRecorder.stopRecording();
     	  }
           break;
     	case "playing":    //点击播放按钮的回调
            name = arguments[1];
            $('#'+name+'_playing').val(1);
            isPlaying = true;
            break;
          case "playback_started":  //播放中的回调
            name = arguments[1];
            var latency = arguments[2].toFixed(0); 
          //  $('#'+name+'_total').val(latency);
            break;
          case "stopped":           //停止按钮的回调
            name = arguments[1];
            $('#'+name+'_playing').val(-1);
            $('#'+name+'_pause').val(0);
            /*$('#'+name).find('.pause').hide();
            $('#'+name).find('.start-playing').show();
            $('#'+name).find('.mbar .pbar').css({width: 100 + '%'});*/
            isPlaying = false;
            break;
     	case "playing_paused":
            FWRecorder.stopObservingLevel();
            $('#'+name+'_playing').val(-1);
            isPlaying = false;
     	   break;
     }
};