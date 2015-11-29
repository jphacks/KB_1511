$(function(){
  $("#sendButton").click(function (e) {
    e.preventDefault();
    // フォームを取得
    var form = $('#sendForm');
    var param = {};
    // フォームの内容を取得
    // serializeArrayの戻り値は[{key: 'キーの名前', value: '値'}]という形式になっている
    $(form.serializeArray()).each(function(i, v) {
      param[v.name] = v.value;
    });
    var json = JSON.stringify(param);

  });
});
