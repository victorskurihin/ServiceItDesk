/*
 * tasks.js
 * This file was last modified at 2019-02-16 22:49 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentTask;
var newStatus;

function setTriggers() {
    // Trigger search when pressing 'Return' on search key input field
    $('#searchKey').keypress(function(e){
        if(e.which === 13) {
            search($('#name').val());
            e.preventDefault();
            return false;
        }
    });

    $('#btnAdd').click(function() {
        addTask();
        return false;
    });
}

function newTask() {
    currentTask = {};
    // renderDetails(currentTask);
}

function addTask() {
    console.log('addTask');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        statusCode: {
            201: function(data, textStatus, jqXHR){
                alert("Task created successfully");
                newTask();
            },
            406: function(data, textStatus, jqXHR){
                alert('addTask error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addTask FATAL error: ' + textStatus);
            }
        }
    });
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var consumer = $('#consumer');
    var taskConsumerId = consumer.find('option:selected').val();
    var taskConsumerName = consumer.find('option:selected').text();

    return JSON.stringify({
        "id": Number("0"),
        "title": $('#title').val(),
        "consumer": {"id": Number(taskConsumerId), "name": taskConsumerName, "description": null},
        "status": {"id": Number(STATUS_NEW), "status": newStatus, "description": null},
        "description": $('#description').val()
    });
}

function renderListStatus(data) {
    console.log('renderListGroup');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $.each(list, function(index, status) {
        if (STATUS_NEW === status.id) {
            newStatus = status.status;
            $('#status').html('Статус: ' + status.status);
        }
    });
}

function renderListUser(data) {
    console.log('renderListUser');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var consumerSelect = $('#consumer');
    var consumerId = Number(consumerSelect.find('option:selected').val());

    $.each(list, function(index, user) {
        if (consumerId !== user.id) {
            console.log('renderListUser consumer append: ' + user.id + ' ' + user.name);
            consumerSelect.append('<option value="' + user.id + '">' + user.name + '</option>');
        }
    });
    $('.dropdown-sin-1').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function findAllUsers() {
    console.log('findAllUsers');
    $.ajax({
        type: 'GET',
        url: userRootURL,
        dataType: "json", // data type of response
        success: renderListUser
    });
}

function findAllStatuses() {
    console.log('findAllStatuses');
    $.ajax({
        type: 'GET',
        url: statusRootURL,
        dataType: "json", // data type of response
        success: renderListStatus
    });
}

function find() {
    findAllStatuses();
    findAllUsers();
    setTriggers();
}

jQuery(document).ready(find());
