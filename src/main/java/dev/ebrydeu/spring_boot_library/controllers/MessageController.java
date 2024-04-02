package dev.ebrydeu.spring_boot_library.controllers;

public class MessageController {

    @PutMapping("/messages/{id}/public")
    public MessageDto makeMessagePublic(@PathVariable Long id) {
        return makeMessagePublic(id);
    }

    @PutMapping("/{id}/private/{status}")
    public Object setMessagePrivate(@PathVariable Long id, @PathVariable boolean status) {
        return messageService.setMessagePrivate(status, id);
    }
}
