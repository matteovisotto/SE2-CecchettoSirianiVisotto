$(function () {

    const downloadController = new DownloadController( $('#downloadContainer'), $('#downloadBtn'), $('#dataSetIdText'));
    downloadController.addListener();

    function DownloadController(_container, _downloadBtn, _dataSetIdText) {
        var self = this;
        this.container = _container;
        this.downloadBtn = _downloadBtn;
        this.dataSetIdText = _dataSetIdText;

        this.loadData = function (dataSetId) {
            $.get('../../api/data/dataset/' + dataSetId, (json) => {
                if(json.length === 0) {
                    self.container.append('<div class="alert alert-danger">The dataSet selected is empty</div>');
                } else {
                    self.download(json);
                }
            }).fail((error) => {
                self.container.append('<div class="alert alert-danger">An error occurred. Please try later</div>');
            });
        }
        this.download = function (json) {
            var filename = "dataset.json";
            var element = document.createElement('a');
            element.setAttribute('href','data:text/plain;charset=utf-8, ' + encodeURIComponent(json));
            element.setAttribute('download', filename);
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
        }
        this.addListener = function () {
            self.downloadBtn.addEventListener("click", function () {
                var dataSetId = self.dataSetIdText.value();
                self.loadData(dataSetId);
                }, false);
        }
    }
});