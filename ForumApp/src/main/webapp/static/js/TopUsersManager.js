$(function(){

    const topUserController = new TopUserController($('#topUsers'));
    topUserController.loadData();

    function myPost(_container){
        var self = this;
        this.container = _container;

        this.loadData = function(){
            this.container.html("");
            $.get('api/user/active', (json) => {
                json.forEach(u => {
                    self.createNode(u);
                });
            }).fail((error) => {
                self.container.append('<div class="alert alert-danger">An error occurred. Please try later</div>');
            });
        }
    }

    function TopUserController(_container){
        var self = this;
        this.container = _container;

        this.loadData = function(){
            this.container.html("");
            $.get('api/user/active', (json) => {
                json.forEach(u => {
                    self.createNode(u);
                });
            }).fail((error) => {
                self.container.append('<div class="alert alert-danger">An error occurred. Please try later</div>');
            });
        }

        this.createNode = function(user) {
            var image = '<img src="static/assets/placeholder_user.png" class="card-profile" width="50%" height="50%"/>';
            var imageContainer = $('<div/>').addClass('col-2 d-inline-flex').append(image);
            var h4Node = $('<h4/>').addClass('card-title').text(user.name + " " + user.surname);
            var textContainer = $('<div/>').addClass('col-10').append(h4Node);
            var card = $('<div/>').addClass('card border-0 dream-card').append($('<div/>').addClass('card-body').append($('<div/>').addClass('row').append(imageContainer).append(textContainer)));

            this.container.append(card);
        }
    }

});