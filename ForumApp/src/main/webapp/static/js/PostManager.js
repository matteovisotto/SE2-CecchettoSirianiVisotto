$(function () {
    var repliesContainer = $('#repliesContainer');
    var discussionTitle = $('#discussionTitle');

    $.ajax(
        {
            'url': '../api/discussion/'+discussionId,
            'method': 'GET',
            'success': function(json){
                    var first = json.posts[0];
                    createPrimaryDiscussion(first, json);
                    json.posts.shift();
                    json.posts.forEach(d => {
                        createReplyNode(d, json);
                    });

            },
            'error':function(){
                alert('Invalid discussion');
            }
        }
    );

    function createPrimaryDiscussion(p, d){
        discussionTitle.text(d.title);
        $('#discussionText').html(p.text);
        $('#discussionCreatorName').text(p.creator.name+' '+p.creator.surname);
        $('#discussionCreatorArea').text('District of '+ p.creator.areaOfResidence);
    }


    function createReplyNode(p, t) {
        var image = "<img src=\"../static/assets/placeholder_user.png\" class=\"card-profile\">";
        var username = $('<strong/>').addClass('text-primary').text(p.creator.name+' '+p.creator.surname);
        var badge = $('<div/>');
        if(p.creator.isPolicyMaker){
            badge.addClass('badge bg-success').text("Policy Maker");
        } else {
            badge.addClass('badge bg-warning').text("User");
        }
        var area = $('<span/>').addClass('text-secondary small').text('District of '+p.creator.areaOfResidence);
        var userInfo = $('<div/>').addClass('col-3 d-flex').append(
            $('<div/>').addClass('vstack gap-1 mx-auto align-items-center')
                .append(image)
                .append(username)
                .append(badge)
                .append(area)
        );
        var postContent = $('<div/>').addClass('col-9');
        if((typeof isPolicyMaker !== 'undefined' && typeof userId !== 'undefined') && (isPolicyMaker || p.creator.userId===userId)){
            postContent.append('<a class="float-end text-secondary" id="cardDropdown'+p.postId+'" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-ellipsis-v"></i></a>\n' +
                '                                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="cardDropdown'+p.postId+'" >\n' +
                '                                        <li><a class="dropdown-item" href="#"><i class="fas fa-pen"></i> Modify</a></li>\n' +
                '                                        <li><a class="dropdown-item" href="#"><i class="fa fa-trash"></i> Delete</a></li>\n' +
                '                                    </ul>');
        }
        postContent.append($('<div/>').addClass('card-text').html(p.text));
        var postRow = $('<div/>').addClass('row').append(userInfo).append(postContent);
        var container = $('<div/>').addClass("col-12 card-container").append(
            $('<div/>').addClass("card border-0 shadow dream-card").append(
                $('<div/>').addClass("card-body").append(postRow)
            )
        );
        repliesContainer.append(container);
    }


});