var index = {
    init: function () {
        var _this = this;
        document.getElementById('btn-save').addEventListener('click', function () {
            _this.save()
        })
    },
    save: function () {
        var data = {
            title: document.getElementById('title').value,
            author: document.getElementById('author').value,
            content: document.getElementById('content').value
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.')
            window.location.href = '/'
        }).fail(function (error) {
            alert(JSON.stringify(error))
        })
    }
}

index.init()