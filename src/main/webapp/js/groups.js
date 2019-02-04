// rootURL - The root URL for the RESTful services
// Example: var rootURL = "http://localhost:8080/ServiceItDesk/rest/api/v1/groups";

var currentGroup;

function setTriggers() {
    // Nothing to delete in initial application state
    $('#btnDelete').hide();

    // Register listeners
    $('#btnSearch').click(function() {
        search($('#name').val());
        return false;
    });

    // Trigger search when pressing 'Return' on search key input field
    $('#searchKey').keypress(function(e){
        if(e.which == 13) {
            search($('#name').val());
            e.preventDefault();
            return false;
        }
    });

    $('#btnAdd').click(function() {
        newGroup();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#groupId').val() == '')
            addGroup();
        else
            updateGroup();
        return false;
    });

    $('#btnDelete').click(function() {
        deleteGroup();
        return false;
    });

    $('#groupList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
	if (searchKey == '')
		findAll();
	else
		findByName(searchKey);
}

function newGroup() {
	$('#btnDelete').hide();
	currentGroup = {};
	renderDetails(currentGroup); // Display empty form
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
}

function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentGroup = data;
			renderDetails(currentGroup);
		}
	});
}

function addGroup() {
	console.log('addGroup');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Group created successfully');
			$('#btnDelete').show();
			$('#groupId').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addGroup error: ' + textStatus);
		}
	});
}

function updateGroup() {
	console.log('updateGroup');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Group updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateGroup error: ' + textStatus);
		}
	});
}

function deleteGroup() {
	console.log('deleteGroup');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#groupId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Group deleted successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteGroup error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#groupList li').remove();
	$.each(list, function(index, group) {
		$('#groupList').append('<li><a href="#" data-identity="' + group.id + '">'+group.name+'</a></li>');
	});
	setTriggers();
}

function renderDetails(group) {
	$('#groupId').val(group.id);
	$('#name').val(group.name);
	$('#description').val(group.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var groupId = $('#groupId').val();
	return JSON.stringify({
		'"id"': groupId == "" ? Number("0") : Number(groupId),
		'"name"': $('#name').val(),
		'"description"': $('#description').val()
		});
}

// Retrieve group list when application starts
jQuery(document).ready(findAll());
