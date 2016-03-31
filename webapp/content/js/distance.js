// Models
window.Distance = Backbone.Model.extend({
    urlRoot:"rest/distance"
});

window.DistanceCollection = Backbone.Collection.extend({
    model:Distance,
    url:"rest/distance"
});

// Views
window.DistanceListView = Backbone.View.extend({

    tagName:'ul',

    initialize:function () {
        this.model.bind("reset", this.render, this);
        var self = this;
        this.model.bind("add", function (distance) {
            $(self.el).append(new DistanceListItemView({model:distance}).render().el);
        });
    },

    render:function (eventName) {
        _.each(this.model.models, function (distance) {
            $(this.el).append(new DistanceListItemView({model:distance}).render().el);
        }, this);
        return this;
    }
});

window.DistanceListItemView = Backbone.View.extend({

    tagName:"li",

    template:_.template($('#tpl-distance-list-item').html()),

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

window.DistanceView = Backbone.View.extend({

    template:_.template($('#tpl-distance-details').html()),

    initialize:function () {
        this.model.bind("change", this.render, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    events:{
        "change input":"change",
        "click .save":"saveDistance",
        "click .delete":"deleteDistance"
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        // You could change your model on the spot, like this:
        // var change = {};
        // change[target.name] = target.value;
        // this.model.set(change);
    },

    saveDistance:function () {
        this.model.set({
            name:$('#name').val(),
            latitude:$('#latitude').val(),
            longitude:$('#longitude').val()
        });
        if (this.model.isNew()) {
            var self = this;
            app.distanceList.create(this.model, {
                success:function () {
                    alert('Distance added successfully');
                    app.navigate('cities/' + self.model.id, false);
                }
            });
        } else {
            this.model.save({
                success:function () {
                    alert('Distance updated successfully');
                }
            });
        }

        return false;
    },

    deleteDistance:function () {
        this.model.destroy({
            success:function () {
                alert('Distance deleted successfully');
                if (app.distanceView) app.distanceView.close();
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

   initialize:function () {
        this.render();
    },

    render:function (eventName) {
        $(this.el).html(this.template());
        return this;
    },

    events:{
        "click .new":"newDistance"
    },

    newDistance:function (event) {
        app.navigate("cities/new", true);
        return false;
    }
});


// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list",
        "distance/:id":"distanceOneDistance",
        "distance/:id1/:id2":"distanceTwoDistance"
    },

    initialize:function () {
        $('#header').html(new HeaderView().render().el);
    },

    list:function () {
        this.distanceList = new DistanceCollection();
        var self = this;
        this.distanceList.fetch({
            success:function () {
                self.distanceListView = new DistanceListView({model:self.distanceList});
                $('#sidebar').html(self.distanceListView.render().el);
            },
        	data: $.param( 
        		{ 
        			return: $("#combo-unidade").val(),
        			page: $("#combo-unidade").val()
        		
        		})
        });
    },

    distanceDetails:function (id) {
        if (this.distanceList) {
            this.distance = this.distanceList.get(id);
            if (this.distanceView) this.distanceView.close();
            this.distanceView = new DistanceView({model:this.distance});
            $('#content').html(this.distanceView.render().el);
        } else {
            this.requestedId = id;
            this.list();
        }
    },

    newDistance:function () {
        if (app.distanceView) app.distanceView.close();
        app.distanceView = new DistanceView({model:new Distance()});
        $('#content').html(app.distanceView.render().el);
    },
    
    distanceAllDistance:function () {
    	this.distanceList = new DistanceCollection();
        var self = this;
        this.distanceList.fetch({
            success:function () {
                self.distanceListView = new DistanceListView({model:self.distanceList});
                $('#distance').html(self.distanceListView.render().el);
                if (self.requestedId) self.distanceDetails(self.requestedId);
            }
        });
    },
    
    distanceOneDistance:function (id) {
        if (this.distanceList) {
            this.distance = this.distanceList.get(id);
            if (this.distanceView) this.distanceView.close();
            this.distanceView = new DistanceView({model:this.distance});
            $('#content').html(this.distanceView.render().el);
        } else {
            this.requestedId = id;
            this.list();
        }
    },
    
    distanceTwoDistance:function (id1,id2) {
        if (this.distanceList) {
            this.distance = this.distanceList.get(id);
            if (this.distanceView) this.distanceView.close();
            this.distanceView = new DistanceView({model:this.distance});
            $('#content').html(this.distanceView.render().el);
        } else {
            this.requestedId = id;
            this.list();
        }
    }

});

var app = new AppRouter();
Backbone.history.start();