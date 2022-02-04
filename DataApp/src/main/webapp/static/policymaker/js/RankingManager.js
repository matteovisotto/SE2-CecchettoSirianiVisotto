$(function(){

    const rankingController = new RankingController($('#rankingContainer'), $('#firstDataSet'), $('#secondDataSet'), $('#recalculateBtn'));
    rankingController.addListener();
    rankingController.loadData();

    function RankingController(_container, _firstDataSetCheckBox, _secondDataSetCheckBox, _recalculateBtn){
        var self = this;
        this.container = _container;
        this.firstDataSetCheckBox = _firstDataSetCheckBox;
        this.secondDataSetCheckBox = _secondDataSetCheckBox;
        this.recalculateBtn = _recalculateBtn;
        var districtId = '19_1'

        this.loadData = function(){
            this.container.html("");
            $.get('../api/data/ranking/' + districtId, (json) => {
                var c = 0;
                json.forEach(r => {
                    self.createNode(r);
                });
            }).fail((error) => {
                self.container.append('<div class="alert alert-danger mt-2">An error occurred. Please try later</div>');
            });
        }

        this.recalculateDeviance = function(firstDataSetValue, secondDataSetValue){
            this.container.html("");

        }

        this.createNode = function(ranking) {
            var position = $('<td/>').addClass('text-center align-middle').text(ranking.position);
            var area = $('<strong/>').text(ranking.district);
            var content = $('<p/>').text(ranking.zone);
            var contentZone = $('<td/>').addClass('align-middle').append(area).append(content);
            var value = $('<p/>').text(ranking.value);
            var valueZone = $('<td/>').addClass('align-middle').append(value);
            var arrow = $('<i/>').addClass('fas fa-arrow-up');
            var arrowZone = $('<td/>').addClass('align-middle').append(arrow);
            var tableNode = $('<tr/>').append(position).append(contentZone).append(valueZone).append(arrowZone);
            this.container.append(tableNode);
        }

        this.addListener = function () {
            self.recalculateBtn.on("click", function () {
                var firstDataSetValue = self.firstDataSetCheckBox.val();
                var secondDataSetValue = self.secondDataSetCheckBox.val();
                self.recalculateDeviance(firstDataSetValue, secondDataSetValue);
            });
        }
    }

});