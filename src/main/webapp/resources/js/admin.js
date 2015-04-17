/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//{
//theme: "modern",
//    plugins: [
//        "advlist autolink lists link image charmap print preview hr anchor pagebreak",
//        "searchreplace wordcount visualblocks visualchars code fullscreen",
//        "insertdatetime media nonbreaking save table contextmenu directionality",
//        "emoticons template paste textcolor colorpicker textpattern"
//    ],
//    toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
//    toolbar2: "print preview media | forecolor backcolor emoticons",
//    image_advtab: true
//    }
// TinyMCE configuration
$('textarea.tinymce').tinymce({
    theme: "modern",
    plugins: [
        "advlist autolink lists link image charmap print preview hr anchor pagebreak",
        "searchreplace wordcount visualblocks visualchars code fullscreen",
        "insertdatetime media nonbreaking save table contextmenu directionality",
        "emoticons template paste textcolor colorpicker textpattern"
    ],
    toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image print preview media | forecolor backcolor emoticons",
    image_advtab: true
});


