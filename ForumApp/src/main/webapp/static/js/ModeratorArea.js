$(function () {

    var repliesContainer = $('#moderatorRepliesContainer');

    loadData();

    function loadData() {
        repliesContainer.html("");
        $.ajax(
            {
                'url': 'api/post/pending',
                'method': 'GET',
                'success': function(json){
                    json.forEach(d => {
                        createReplyNode(d);
                    });
                },
                'error':function(){
                    alert('Invalid discussion');
                }
            }
        );
    }


    function createReplyNode(p) {
        var image = '<img src="static/assets/placeholder_user.png" class="card-profile">';
        var username = $('<strong/>').addClass('text-primary').text(p.creator.name+' '+p.creator.surname);
        var badge = $('<div/>');
        badge.addClass('badge bg-warning').text("User");
        var area = $('<span/>').addClass('text-secondary small').text('District of '+p.creator.areaOfResidence);
        var userInfo = $('<div/>').addClass('col-3 d-flex').append(
            $('<div/>').addClass('vstack gap-1 mx-auto align-items-center')
                .append(image)
                .append(username)
                .append(badge)
                .append(area)
        );
        var postContent = $('<div/>').addClass('col-9');
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