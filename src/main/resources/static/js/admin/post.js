$(document).ready(function () {
    const editorContainer = document.getElementById('editor');
    const postReview = $(".post-review");

    const editor = CKEDITOR.replace(editorContainer, {
        height: 300,
    });

    editor.on('change', function (evt) {
        // getData() returns CKEditor's HTML content.
        postReview.html(evt.editor.getData());
    });

    CKEDITOR.instances.editor.on('save', function (event) {
        console.log(event.editor.getData());
        savePost(event.editor.getData());
        return false;
    })

    function savePost(content) {
        const data = {
            title: $("#title").val(),
            content: content,
            category: $("#category").val(),
            author: $("#id").val(),
            imageUri: $("#image").val(),
            sortDescription: $("#sortDescription").val(),
        }

        $.ajax({
            url: '/api/post/add',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                window.location.href = "/admin/post";
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
})