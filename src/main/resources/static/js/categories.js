$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, _status){
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.name);
        });
        $('#editModal').modal();
    });
});