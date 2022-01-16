$(function () {
    var discussionContainer = $('#discussionContainer');
    $.ajax(
        {
            'url': '../api/discussion/'+topicId,
            'method': 'GET',
            'success': function(json){
                if (json.length === 0) {
                    createEmptyTopicAlert();
                } else {
                    json.forEach(d => {
                        createDiscussionNode(d);
                    });
                }
            },
            'error':function(){
                alert('Error!');
            }
        }
    );

    function createEmptyTopicAlert(){
        var alert = $('<div/>');
        alert.addClass('alert alert-primary').text("No discussions available");
        discussionContainer.append(alert);
    }

    function createDiscussionNode(d) {

    }

});