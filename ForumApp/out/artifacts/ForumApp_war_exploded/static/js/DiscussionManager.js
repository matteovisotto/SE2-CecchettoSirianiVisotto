$(function () {
    var discussionContainer = $('#discussionContainer');
    /*$.ajax(
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
    );*/
    const fake = [
        {
            discussionId: 1,
            topic: {topicId: 1, title: "Topic title"},
            creator: {name: "Name", surname: "Surname", role:"policy_maker", areaOfResidence:"District of X", memberSince:"2022"},
            title: "Discussion title",
            createdAt: "2022-01-16",
            content: "A part of the text of the first post",
            nReplies: 10,
            rank: "up"
        },
        {
            discussionId: 2,
            topic: {topicId: 1, title: "Topic title"},
            creator: {name: "Name", surname: "Surname", role:"policy_maker", areaOfResidence:"District of X", memberSince:"2022"},
            title: "Discussion title",
            createdAt: "2022-01-16",
            content: "A part of the text of the first post",
            nReplies: 10,
            rank: "down"
        }
    ];
    fake.forEach(d => {
        createDiscussionNode(d);
    })

    function createEmptyTopicAlert(){
        var alert = $('<div/>');
        alert.addClass('alert alert-primary').text("No discussions available");
        discussionContainer.append(alert);
    }

    function createDiscussionNode(d) {
        var className = 'vstack gap-0 text-danger mx-auto align-items-center';
        var rank = $('<div/>').addClass(className).append($('<i/>').addClass('fas fa-sort-up fa-2x text-secondary')).append($('<strong/>').text(d.nReplies)).append($('<i/>').addClass('fas fa-sort-down fa-2x'));
        if(d.rank==="up"){
            className = 'vstack gap-0 text-primary mx-auto align-items-center';
            rank = $('<div/>').addClass(className).append($('<i/>').addClass('fas fa-sort-up fa-2x')).append($('<strong/>').text(d.nReplies)).append($('<i/>').addClass('fas fa-sort-down fa-2x  text-secondary'));
        }

        var rankNode = $('<div/>').addClass('col-1 d-flex').append(rank);

        var topicNode = $('<span/>').addClass('text-secondary float-end small').text("Topic: " + d.topic.title);
        var h4Node = $('<h4/>').addClass('card-title').text(d.title);
        var descriptionNode = $('<p/>').addClass('card-text').text(d.content);
        var authorNode = '<table class="table table-borderless text-secondary"><tbody><tr class="small">' +
            '<td><img src="../static/assets/placeholder_user.png" class="top-user-img"> Posted' +
            'by: <strong>'+d.creator.name + ' ' + d.creator.surname+'</strong></td>' +
            '<td>' + d.createdAt + '</td>' +
            '<td class="text-sm-end"><i class="fas fa-comments"></i> '+ d.nReplies +'</td>' +
            '</tr></tbody></table>';
        var textContainer = $('<div/>').addClass('col-11').append(topicNode).append(h4Node).append(descriptionNode).append('<hr/>').append(authorNode);

        var card = $('<div/>').addClass('card border-0 shadow dream-card').append($('<div/>').addClass('card-body').append($('<div/>').addClass('row').append(rankNode).append(textContainer)));

        var aNode = $('<a/>').addClass('link-card').attr('href', '../discussion/'+d.discussionId).append(card);

        var container = $('<div/>').addClass('col-12 card-container').append(aNode);

        discussionContainer.append(container);


    }

});