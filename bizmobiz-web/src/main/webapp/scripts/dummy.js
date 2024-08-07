// The dummy view model
function DummyViewModel() {
    var self = this;
    
    self.dummies = ko.observableArray([]);
    
    var getUrl = window.location;
    var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
    
    // load dummies from server: GET on dummy rest controller
    self.loadDummies = function () {
        $.ajax(baseUrl+"/api/country", {
            type: "GET",
            success: function (data) {
                if (data.length > 0) {
                    self.dummies(data);
                } else {
                    self.dummies([]);
                }
            }
        });
    };

    // Load initial data
    self.loadDummies();
}

// Activate knockout.js
ko.applyBindings(new DummyViewModel());