// Models
window.City = Backbone.Model.extend({
    urlRoot:"rest/cities",
    defaults:{
        "id":null,
        "name":"",
        "latitude":"",
        "longitude":""
    },
    validateData:function(){
        if(this.attributes.name == "") return false;
        if(this.attributes.latitude == "" || this.attributes.latitude > 90 || this.attributes.latitude < -90) return false;
        if(this.attributes.longitude == "" || this.attributes.longitude > 180 || this.attributes.longitude < -180) return false;   
        return true;
    }
});

window.CityCollection = Backbone.Collection.extend({
    model:City,
    url:"rest/cities"
});


// Views
window.CityListView = Backbone.View.extend({

    tagName:'div',
    className:"list-group",

    initialize:function () {
        this.model.bind("reset", this.render, this);
        var self = this;
        this.model.bind("add", function (city) {
            $(self.el).append(new CityListItemView({model:city}).render().el);
        });
    },

    render:function (eventName) {
        _.each(this.model.models, function (city) {
            $(this.el).append(new CityListItemView({model:city}).render().el);
        }, this);
        return this;
    }
});

window.CityListItemView = Backbone.View.extend({

    tagName:"div",

    template:_.template($('#tpl-city-list-item').html()),

    initialize:function () {
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    close:function () {
        $(this.el).unbind();
        $(this.el).remove();
    }
});

window.CityView = Backbone.View.extend({

    template:_.template($('#tpl-city-details').html()),

    initialize:function () {
        this.model.bind("change", this.render, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    events:{
        "change input":"change",
        "click .save":"saveCity",
        "click .delete":"deleteCity"
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        // You could change your model on the spot, like this:
        // var change = {};
        // change[target.name] = target.value;
        // this.model.set(change);
    },

    saveCity:function () {
        this.model.set({
            name:$('#name').val(),
            latitude:$('#latitude').val(),
            longitude:$('#longitude').val()
        });
        if(!this.model.validateData()){
            alert("City with invalid info.");
            return false;
        }
            
            
        if (this.model.isNew()) {
            var self = this;
            app.cityList.create(this.model, {
                success:function () {
                    alert('City added successfully');
                    app.navigate('cities/' + self.model.id, false);
                },
                error: function(model, response){
                    alert(response.responseJSON.message);
                }
            });
        } else {
            this.model.save(
                {},{
                success:function () {
                    alert('City updated successfully');
                },
                error: function(model, response){
                    alert(response.responseJSON.message);
                }
            });
        }

        return false;
    },

    deleteCity:function () {
        this.model.destroy({
            success:function () {
                alert('City deleted successfully');
                if (app.cityView) app.cityView.close();
            },
            error: function(model, response){
                alert(response.responseJSON.message);
            }
        	
        });
        return false;
    },

    close:function () {
        $(this.el).unbind();
        $(this.el).empty();
    }
});

window.HeaderView = Backbone.View.extend({
	 
    template:_.template($('#tpl-header').html()),
 
    initialize:function () {
        this.render();
    },
 
    render:function (eventName) {
        $(this.el).html(this.template());
        return this;
    },
 
    events:{
        "click .new":"newCity"
    },
 
    newCity:function (event) {
        if (app.cityView) app.cityView.close();
        app.cityView = new CityView({model:new City()});
        $('#content').html(app.cityView.render().el);
        return false;
    }
});




// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list",
        "cities/new":"newCity",
        "cities/:id":"cityDetails",
    },

    initialize:function () {
        $('#header').html(new HeaderView().render().el);
    },

    list:function () {
        this.cityList = new CityCollection();
        var self = this;
        this.cityList.fetch({
            success:function () {
                self.cityListView = new CityListView({model:self.cityList});
                $('#sidebar').html(self.cityListView.render().el);
                if (self.requestedId) self.cityDetails(self.requestedId);
            }
        });
    },

    cityDetails:function (id) {
        if (this.cityList) {
            this.city = this.cityList.get(id);
            if (this.cityView) this.cityView.close();
            this.cityView = new CityView({model:this.city});
            $('#content').html(this.cityView.render().el);
        } else {
            this.requestedId = id;
            this.list();
        }
    },

    newCity:function () {
        if (app.cityView) app.cityView.close();
        app.cityView = new CityView({model:new City()});
        $('#content').html(app.cityView.render().el);
    }

});

var app = new AppRouter();
Backbone.history.start();