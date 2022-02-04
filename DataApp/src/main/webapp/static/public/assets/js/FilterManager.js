$(function () {

    const downloadController = new DownloadController($('#filterBtn'), $('#districtId'), $('#filteredDataContainer'));
    downloadController.addListener();

    function DownloadController(_filterBtn, _districtId, _filteredDataContainer) {
        var self = this;
        this.filterBtn = _filterBtn;
        this.districtId = _districtId;
        this.filteredDataContainer = _filteredDataContainer;

        this.loadData = function (districtId) {
            self.filteredDataContainer.html("");
            $.get('api/data/filter/' + districtId, (json) => {
                if(json.length === 0) {
                    self.filteredDataContainer.append('<div class="alert alert-danger">The dataSet selected is empty for this district</div>');
                } else {
                    self.createNode(json);
                }
            }).fail((error) => {
                self.filteredDataContainer.append('<div class="alert alert-danger">An error occurred. Please try later</div>');
            });
        }

        this.createNode = function(json) {
            var data = $('<p/>').text(JSON.stringify(json));
            this.filteredDataContainer.append(data);
        }

        this.addListener = function () {
            self.filterBtn.on("click", function () {
                var districtId = self.districtId.val();
                self.loadData(districtId);
                });
        }
    }
});