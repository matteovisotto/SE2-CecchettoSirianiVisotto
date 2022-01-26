$(function () {

    var MODAL_ACTION = "update";
    var current_selected_post = undefined;

    if(typeof userId !== 'undefined'){
        CKEDITOR.replace('replyContent');

        $('#modalCreateReplyButton').on('click', function (e){
            var form = $('#approveReplyForm');
            if(MODAL_ACTION === 'update'){
                if(current_selected_post !== undefined){
                    current_selected_post.text = CKEDITOR.instances.replyContent.getData();
                    current_selected_post.timestamp = new Date();
                    $.ajax({
                        type: "POST",
                        url: "api/post/modify",
                        data: JSON.stringify(current_selected_post),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        success: function(data){
                            $('#approveReplyModal').modal('hide');
                            CKEDITOR.instances.replyContent.setData("");
                            //$('#modalUpdateButton').text("Approve");
                            Swal.fire({
                                icon: 'success',
                                title: 'Post successfully modified',
                                showConfirmButton: false,
                                timer: 1500
                            }).then((r) => {
                                //Qua andrebbe chiamata la approvePost penso
                                loadData();
                            });
                        },
                        error: function(e) {
                            Swal.fire({
                                icon: 'error',
                                title: 'An error occurred. Please try again',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                    });
                }
            }

        });

    }

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
            MODAL_ACTION = "update";
            var postId = $(e.target).data('post-id');
            fetch('api/post/'+postId).then(response => {
                return response.json();
            }).then(json => {
                current_selected_post = json;
                CKEDITOR.instances.replyContent.setData(json.text);
                $('#modalUpdateButton').text("Approve");
                $('#approveReplyModal').modal('show');
            });
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
                confirmButtonText: 'Yes, decline it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.post( "api/post/delete/"+postId, function( data ) {
                        Swal.fire(
                            'Declined!',
                            'This post has been deleted.',
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
        postContent.append('<a class="float-end text-secondary" id="cardDropdown'+p.postId+'" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-ellipsis-v"></i></a>\n' +
                '                                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="cardDropdown'+p.postId+'" >\n' +
                '                                        <li><a class="dropdown-item modifyDropdown" href="#" data-post-id="'+p.postId+'"><i class="fas fa-pen"></i> Modify</a></li>\n' +
                '                                        <li><a class="dropdown-item deleteDropdown" href="#" data-post-id="'+p.postId+'"><i class="fa fa-trash"></i> Decline</a></li>\n' +
                '                                    </ul>');
        postContent.append($('<h6/>').addClass('text-primary').html('Discussion: ' + p.discussionId));
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