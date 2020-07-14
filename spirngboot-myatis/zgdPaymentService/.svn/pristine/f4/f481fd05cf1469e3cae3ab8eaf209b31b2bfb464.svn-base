String.prototype.substitute = function (a) {
    return a&&"object"==typeof a?this.replace(/\{([^{}]+)\}/g,function(b,c){var d=a[c];return void 0!==d?""+d:""}):this.toString()
}
function _Dialog(options) {
    var _this = this;
    var Tmp = [
        '<div class="flower-dialog">',
        '<span class="dialog-close">X</span>',
        '<h4 class="title">{title}</h4>',
        '<div class="d-content">{content}</div>',
        '</div>'
    ].join('');
    if(typeof options !== 'object'){
        this.title = '消息提示';
        this.content = options;
    }else{
        this.title = options.title;
        this.content = options.content;
    }
    var HTML = Tmp.substitute({
        title : this.title,
        content : this.content
    })
    var body = document.body;
    this.dom = document.createElement("div");
    this.dom.className = 'mask';
    this.dom.id = 'onlyMask';
    this.dom.innerHTML = HTML;
    body.appendChild(this.dom);
    var closeBtn = this.dom.getElementsByClassName('dialog-close')[0];
    closeBtn.addEventListener('touchstart',function () {
        _this.remove();
    },false)
}
_Dialog.prototype.remove = function () {
    document.body.removeChild(this.dom)
}
function dialog(agu){
    return new _Dialog(agu);
}
