$(function () {

    var discussionContainer = $('#discussionContainer');
    var topicTitle = $('#topicTitle');
    $.ajax(
        {
            'url': '../api/discussion/explore',
            'method': 'GET',
            'success': function(json){
                topicTitle.text(json.title);
                if (json.discussions.length === 0) {
                    createEmptyTopicAlert();
                } else {
                    json.discussions.forEach(d => {
                        createDiscussionNode(d, json);
                    });
                }
            },
            'error':function(){
                alert('Invalid topic');
            }
        }
    );


    function createEmptyTopicAlert(){
        var alert = $('<div/>');
        alert.addClass('alert alert-primary').text("No discussions available");
        discussionContainer.append(alert);
    }

    function createDiscussionNode(d, t) {
        var className = 'vstack gap-0 text-secondary mx-auto align-items-center';
        var rank = $('<div/>').addClass(className).append($('<i/>').addClass('fas fa-sort-up fa-2x text-secondary')).append($('<strong/>').text(d.number_replies)).append($('<i/>').addClass('fas fa-sort-down fa-2x text-secondary'));

        var rankNode = $('<div/>').addClass('col-1 d-flex').append(rank);

        var topicNode = $('<span/>').addClass('text-secondary float-end small').text("Topic: " + t.title);
        var h4Node = $('<h4/>').addClass('card-title').text(d.title);
        var descriptionNode = $('<p/>').addClass('card-text').text(d.text);
        var authorNode = '<table class="table table-borderless text-secondary"><tbody><tr class="small">' +
            '<td><img src="../static/assets/placeholder_user.png" class="top-user-img"> Posted' +
            'by: <strong>'+d.creator.name + ' ' + d.creator.surname+'</strong></td>' +
            '<td>' + d.timestamp + '</td>' +
            '<td class="text-sm-end"><i class="fas fa-comments"></i> '+ d.number_replies +'</td>' +
            '</tr></tbody></table>';
        var textContainer = $('<div/>').addClass('col-11').append(topicNode).append(h4Node).append(descriptionNode).append('<hr/>').append(authorNode);

        var card = $('<div/>').addClass('card border-0 shadow dream-card').append($('<div/>').addClass('card-body').append($('<div/>').addClass('row').append(rankNode).append(textContainer)));

        var aNode = $('<a/>').addClass('link-card').attr('href', '../discussion/'+d.discussionId).append(card);

        var container = $('<div/>').addClass('col-12 card-container').append(aNode);

        discussionContainer.append(container);


    }

});