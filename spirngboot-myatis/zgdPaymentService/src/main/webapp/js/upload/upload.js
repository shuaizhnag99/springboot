// $(function () {
//    var busLiecnceNo = '',
//      busLiecnceImg = '',
//      storePropertyTypeImg = '',
//      certHoldImg = '',
//      storeImg = '',
//      certPositiveImg = '',
//      storeInnerImg = '',
//      certNegativeImg = '';
//    function initUploadify(selector, str) {
//      str=str+'_'+$("#userOnlyId").val();
//      $(selector).uploadify({
//        'uploader': $("#welab_server").val()+'/js/upload/uploadify.swf',
//        'buttonImg': $("#welab_server").val()+'/js/upload/browse.png',
//        'cancelImg': $("#welab_server").val()+'/js/upload/cancel.png',
//        'script': $("#welab_server").val()+'/uploadImg?picType=' + str,
//        'folder': 'upload',
//        'fileDataName': 'logoImg',
//        'auto': true,
//        'multi': false,
//        'simUploadLimit': 5,
//        'queueSizeLimit': 5,
//        'sizeLimit': 10485760,
//        'scriptAccess': 'always',
//        'width': 80,
//        'height': 24,
//        'fileDesc': '请选择JPG/JPNG/GIF/PNG图片',
//        'fileExt': '*.JPG;*.GIF;*.PNG',
//        'onSelect': function () {
//         
//          JEND.page.showLoading();
//        },
//        'onComplete': function (event, queueId, fileObj, response) {
//        
//          JEND.page.hideLoading();
//          var objData = eval("(" + response + ")");
//          var imgUrl = "";
//          if ("0000" == objData.code) {
//            imgUrl = objData.url;
//            
//            
//            switch (event.currentTarget.id) {
//              case 'logoImg':
//                busLiecnceImg = imgUrl;
//                break;
//              case 'logoImg1':
//                certHoldImg = imgUrl;
//                break;
//              case 'logoImg2':
//                storeImg = imgUrl
//                break;
//              case 'logoImg3':
//                certPositiveImg = imgUrl;
//                break;
//              case 'logoImg4':
//                storeInnerImg = imgUrl;
//                break;
//              case 'logoImg5':
//                certNegativeImg = imgUrl;
//                break;
//              case 'logoImg6':
//                storePropertyTypeImg = imgUrl;
//                break;
//            }
//          } else if ("1000" == objData.code) {
//            alert("很抱歉，图片上传失败，可能是网络临时中断的原因，请再试上传一次！");
//            return;
//          } else {
//            alert(objData.msg);
//            return;
//          }
//
//          dealUpload(imgUrl, event.currentTarget.id);
//        },
//        'onError': function (event, queueId, fileObj, errorObj) {
//          alert('onError');
//          JEND.page.hideLoading();
//          if (errorObj.type == 'File Size') {
//            JEND.page.alert('图片大小不能大于10M');
//          } else {
//            JEND.page.alert('上传图片错误:' + errorObj.type + "  " + errorObj.info);
//          }
//          return;
//        },
//        'onAllComplete': function () {
//          JEND.page.hideLoading();
//        }
//      });
//    }
//
//    function dealUpload(imgUrl, obj) {
//      var smallpic = ''
//        if (imgUrl.endWith('.jpg', true)) {
//          smallpic = imgUrl.sliceBefore('.jpg') + '_78x78.jpg'
//        }
//        if (imgUrl.endWith('.jpeg', true)) {
//          smallpic = imgUrl.sliceBefore('.jpeg') + '_78x78.jpeg'
//        }
//        if (imgUrl.endWith('.gif', true)) {
//          smallpic = imgUrl.sliceBefore('.gif') + '_78x78.gif'
//        }
//        if (imgUrl.endWith('.png', true)) {
//          smallpic = imgUrl.sliceBefore('.png') + '_78x78.png'
//        }
//        var html = '<img src="' + smallpic + '" class="pic" width="75" height="75" />',
//        ifra = $("#" + obj).parent().parent().find('.info'),
//        isTrue = false;
//
//      if (!isTrue) {
//        ifra.find('img').remove();
//        ifra.find('.picinfo').before(html);
//        ifra.find('.pic').attr('uploaded_url', imgUrl);
//        ifra.find('.picinfo span.tip').show();
//      } else {
//        ifra.find('.picinfo span.err').hide();
//      }
//    }
//    initUploadify("#logoImg", 'busLience');
//    initUploadify("#logoImg1", 'certHold');
//    initUploadify("#logoImg2", 'store');
//    initUploadify("#logoImg3", 'certPos');
//    initUploadify("#logoImg4", 'storeInner');
//    initUploadify("#logoImg5", 'certNeg');
//    if($('#logoImg6')){
//      initUploadify("#logoImg6", 'storePropertyType');
//    }
//  })