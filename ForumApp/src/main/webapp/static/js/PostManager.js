$(function () {

    if(typeof userId !== 'undefined'){
        CKEDITOR.replace('replyContent');

        $('#modalCreateReplyButton').on('click', function (e){
            var form = $('#newReplyForm');
            var postObj = {};

            var text = CKEDITOR.instances.replyContent.getData();
            postObj.text = text;
            postObj.timestamp = new Date();
            postObj.creator = {"userId": userId};
            postObj.discussionId = discussionId;
            $.ajax({
                type: "POST",
                url: "../api/post/publish",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify(postObj),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){
                    $('#newReplyModal').modal('hide');
                    loadData();
                },
                error: function(e) {
                    alert("Error!");
                }
            });
        });

    }

    var repliesContainer = $('#repliesContainer');
    var discussionTitle = $('#discussionTitle');

    loadData();

    function loadData() {
        repliesContainer.html("");
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
                    registerActions();
                },
                'error':function(){
                    alert('Invalid discussion');
                }
            }
        );
    }

    function registerActions() {
        $('.modifyDropdown').on('click', function (e){
            var postId = $(e.target).data('post-id');

        });

        $('.deleteDropdown').on('click', function (e){
            var postId = $(e.target).data('post-id');
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.post( "../api/post/delete/"+postId, function( data ) {
                        Swal.fire(
                            'Deleted!',
                            'Your file has been deleted.',
                            'success'
                        )
                        loadData();
                    }).fail(function (error){
                        Swal.fire(
                            'Error!',
                            'An error occured while deleting',
                            'error'
                        )
                    });

                }
            });
        });

        $('#discussionDelete').on('click', function (e){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.post( "../api/discussion/delete/"+discussionId, function( data ) {
                        Swal.fire(
                            'Deleted!',
                            'Your file has been deleted.',
                            'success'
                        )
                        window.location.href('../');
                    }).fail(function (error){
                        Swal.fire(
                            'Error!',
                            'An error occured while deleting',
                            'error'
                        )
                    });

                }
            });
        });
    }

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
                '                                        <li><a class="dropdown-item modifyDropdown" href="#" data-post-id="'+p.postId+'"><i class="fas fa-pen"></i> Modify</a></li>\n' +
                '                                        <li><a class="dropdown-item deleteDropdown" href="#" data-post-id="'+p.postId+'"><i class="fa fa-trash"></i> Delete</a></li>\n' +
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