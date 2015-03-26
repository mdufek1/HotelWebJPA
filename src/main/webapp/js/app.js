/*
 * Custom JavaScript for this app
 */

(function ($, window, document) {
    $(function () {

        // declare JQuery selectors and cache results
        var $btnAdd = $('#create');
        var $btnSearch = $('#btnSearch');
        var $btnDelete = $('#delete');
        var $btnSave = $('update');
        var $hotelId = $('#id');
        var $hotelName = $('#name');
        var $hotelAddress = $('#address');
        var $hotelCity = $('#city');
        var $hotelZip = $('#zip');
        var $searchKey = $('#searchKey');
        var baseUrl = "hdc";

        findAll();
        $btnDelete.hide();

        $btnAdd.on('click', function () {
            addHotel();
            return;
        });

        $btnSave.click(function () {
            if ($hotelId.val() === '') {
                addHotel();
            } else {
                updateHotel();
            }
            return false;
        });

        $btnDelete.click(function () {
            deleteHotel();
            return false;
        });

        function clearForm() {
            $hotelId.val("");
            $hotelName.val("");
            $hotelAddress.val("");
            $hotelCity.val("");
            $hotelZip.val("");
        }

        function findAll() {
            $.get(baseUrl).then(function (hotels) {
                renderList(hotels);
            }, handleError);
        }

        function renderList(hotels) {
            $('#hotelList li').remove();
            $.each(hotels, function (index, hotel) {
                $('#hotelList').append('<li><a href="#" id="'
                        + hotel.hotelId + '">' + hotel.name + '</a></li>');
            });
        }

        function handleError(xhr, status, error) {
            alert("Sorry, there was a problem: " + error);
        }

        $('#hotelList').on('click', "a", function () {
            findById($(this).attr("id"));
        });

        function findById(self) {
            $.get(baseUrl+"?op=retrieve&id="+self).then(function (hotel) {
               
                renderDetails(hotel);
                $btnDelete.show();               
            }, handleError);
            return false;
        }

        function renderDetails(hotel) {
            if (hotel.name === undefined) {
                $('#id').val(hotel.hotelId);
            } else {
                var id = hotel.hotelId;
                $('#id').val(id);
            }
            $('#name').val(hotel.name);
            $('#street').val(hotel.address);
            $('#state').val(hotel.state);
            $('#notes').val(hotel.notes);
            $('#city').val(hotel.city);
            $('#zip').val(hotel.zip);
        }

        /*
         * The searchKey is any term that is part of a hotel name, city 
         * or zip.
         */
        $btnSearch.on('click', function () {
            var searchKey = $searchKey.val();
            searchKey = escapeHtml(searchKey.trim());
            var url = "HotelController?action=search&searchKey=" + searchKey;
            $.get(url).then(function (hotel) {
                renderDetails(hotel);
                $btnDelete.show();
            }, handleError);
        });

        var htmlEscapeCodeMap = {
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': '&quot;',
            "'": '&#39;',
            "/": '&#x2F;'
        };

        function escapeHtml(string) {
            return String(string).replace(/[&<>"'\/]/g, function (s) {
                return htmlEscapeCodeMap[s];
            });
        }

        var addHotel = function () {
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: baseUrl + "?op=create",
                dataType: "json",
                data: formToJSON()
            })
            .done(function () {
                findAll();
                $btnDelete.show();
                alert("Hotel added successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Hotel could not be added due to: " + errorThrown);
                console.log(formToJSON());
            });
        }


        var updateHotel = function () {
            console.log('updateHotel');
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: baseUrl + "?op=update",
                dataType: "json",
                data: formToJSON()
            })
            .done(function () {
                findAll();
                $btnDelete.show();
                alert("Hotel updated successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Hotel could not be updated due to: " + errorThrown);
            });
        }

        var deleteHotel = function () {
            console.log('deleteHotel');
            $.ajax({
                type: 'POST',
                url: baseUrl + "?op=delete&id=" + $hotelId.val()
            })
            .done(function () {
                findAll();
                clearForm();
                $btnDelete.hide();
                alert("Hotel deleted successfully");
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Hotel could not be deleted due to: " + errorThrown);
            });
        }

        function renderDetails(hotel) {
            if (hotel.name === undefined) {
                $('#id').val(hotel.hotelId);
            } else {
                var id = hotel.hotelId;
                $('#id').val(id);
            }
            $('#name').val(hotel.name);
            $('#street').val(hotel.address);
            $('#state').val(hotel.state);
            $('#notes').val(hotel.notes);
            $('#city').val(hotel.city);
            $('#zip').val(hotel.zip);
        }

// Helper function to serialize all the form fields into a JSON string
        function formToJSON() {
            return JSON.stringify({
                "id": $hotelId.val(),
                "address": $hotelAddress.val(),
                "city": $hotelCity.val(),
                "name": $hotelName.val(),
                "zip": $hotelZip.val(),
                "notes": $("#notes").val()
            });
        }
    });

}(window.jQuery, window, document));