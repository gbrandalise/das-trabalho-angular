package br.com.ufpr.das.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  // @PostMapping
  //   public ResponseEntity<ClientDTO> insert(@Valid @RequestBody ClientDTO client) {
  //       String errorMessage = "Error insert Client ";
  //       try {
  //           ClientDTO clientSaved = this.clientService.insert(client);
  //           URI uriClient = URI.create("clients/" + clientSaved.getId());
  //           return ResponseEntity.created(uriClient).body(clientSaved);
  //       } catch (IllegalArgumentException e) {
  //           return handleException(errorMessage, e, HttpStatus.BAD_REQUEST);
  //       } catch (Exception e) {
  //           return handleException(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
  //       }
  //   }

}
