package ikecrm.api.resource;

import ikecrm.api.entity.PhotoEntity;
import ikecrm.api.service.PhotoService;
import ikecrm.api.utils.MultipartBody;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.keycloak.common.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/photo")
public class PhotoResource {
    static final Logger log = LoggerFactory.getLogger(PhotoResource.class);

    @Inject
    PhotoService photoService;

    // http://localhost:13372/api/user/photo/7f351d49-0441-4c91-9dfe-b9a3455f3b77
    @GET
    @Path("{uuid}")
    public Response getPhoto(@PathParam("uuid") String uuid){
        var photo = photoService.findByID(uuid);
        try {
            byte[] data = decode(photo);
            var resp = Response.ok(data, photo.getMimeType()).build();
            return resp;
        }catch (IOException ex){
            throw new WebApplicationException(ex.getMessage(), 500);
        }
    }

    // curl -v -X POST --form file='@filename' http://localhost:13372/api/user/photo/7f351d49-0441-4c91-9dfe-b9a3455f3b77
    @POST
    @Path("{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMultipartData(
            @PathParam("uuid") String uuid,
            @MultipartForm MultipartBody data){
        log.info("Receiving POST photo request");
        var base64 = encode(data.getFile());
        var mime = "image/png";
        var photo = new PhotoEntity(uuid, base64, mime);
        photoService.merge(photo);
        return "OK";
    }

    private String encode(InputStream file) {
        try(file) {
            var bytes = file.readAllBytes();
            return Base64.encodeBytes(bytes);
        }catch (IOException ex){
            throw new WebApplicationException(ex.getMessage(), 500);
        }
    }

    private byte[] decode(PhotoEntity photo) throws IOException {
        return Base64.decode(photo.getContent());
    }
}
